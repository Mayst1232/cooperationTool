package com.example.cooperationtool.domain.todo.service;

import static com.example.cooperationtool.global.exception.ErrorCode.NOT_EXIST_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_CARD;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_INVITE_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_USER;

import com.example.cooperationtool.domain.board.entity.InviteBoard;
import com.example.cooperationtool.domain.board.repository.InviteBoardRepository;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.entity.InviteCard;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.card.repository.InviteCardRepository;
import com.example.cooperationtool.domain.todo.dto.TodoModifyRequestDto;
import com.example.cooperationtool.domain.todo.dto.TodoRequestDto;
import com.example.cooperationtool.domain.todo.dto.TodoResponseDto;
import com.example.cooperationtool.domain.todo.entity.Todo;
import com.example.cooperationtool.domain.todo.repository.TodoRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.exception.ErrorCode;
import com.example.cooperationtool.global.exception.ServiceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final InviteCardRepository inviteCardRepository;
    private final CardRepository cardRepository;
    private final InviteBoardRepository inviteBoardRepository;

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto, User user) {
        List<InviteCard> matchingCard = inviteCardRepository.findByCardId(
            todoRequestDto.getCardId());
        var userList = matchingCard.stream()
            .anyMatch(byUser -> byUser.getUser().getId().equals(user.getId()));

        if (userList) {
            Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .nickname(todoRequestDto.getNickname())
                .complete(todoRequestDto.isComplete())
                .build();

            Card card = cardRepository.findById(todoRequestDto.getCardId()).orElseThrow(
                () -> new ServiceException(NOT_FOUND_CARD)
            );

            todo.setCard(card);
            todo.setUser(user);
            Todo savedTodo = todoRepository.save(todo);
            return TodoResponseDto.of(savedTodo);
        } else {
            throw new ServiceException(NOT_EXIST_USER);
        }
    }

    public List<TodoResponseDto> getTodos(Long cardId, User user) {
        Card card = cardRepository.findById(cardId).orElseThrow(
            () -> new ServiceException(NOT_FOUND_CARD)
        );

        InviteBoard inviteBoard = inviteBoardRepository.findByUserAndBoard(user, card.getColumns().getBoard()).orElseThrow(
            () -> new ServiceException(NOT_INVITE_USER)
        );

        List<Todo> todos = todoRepository.findAllByCardId(cardId);

        if (!todos.isEmpty()) {
            return todos.stream()
                .map(TodoResponseDto::of)
                .collect(Collectors.toList());
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_TODO);
        }
    }

    @Transactional
    public TodoResponseDto modifyTodo(Long todoId, TodoModifyRequestDto todoRequestDto, User user) {
        Todo todo = getTodo(todoId);

        if(!todo.getUser().getUsername().equals(user.getUsername())) {
            throw new ServiceException(NOT_MATCH_USER);
        }

        todo.setContent(todoRequestDto.getContent());
        todo.setComplete(todoRequestDto.isComplete());
        todo.updateModifiedAt(LocalDateTime.now());

        return new TodoResponseDto(todo);
    }

    public void deleteTodo(Long todoId, User user) {
        var todo = getTodo(todoId);
        if(!todo.getUser().getUsername().equals(user.getUsername())) {
            throw new ServiceException(NOT_MATCH_USER);
        }

        todoRepository.delete(todo);
    }


    private Todo getTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_FOUND_TODO)
        );
    }


}
