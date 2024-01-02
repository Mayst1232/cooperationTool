package com.example.cooperationtool.domain.todo.service;

import static com.example.cooperationtool.global.exception.ErrorCode.NOT_EXIST_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_CARD;

import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.entity.InviteCard;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.card.repository.InviteCardRepository;
import com.example.cooperationtool.domain.todo.dto.TodoRequestDto;
import com.example.cooperationtool.domain.todo.dto.TodoResponseDto;
import com.example.cooperationtool.domain.todo.entity.Todo;
import com.example.cooperationtool.domain.todo.repository.TodoRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final InviteCardRepository inviteCardRepository;
    private final CardRepository cardRepository;

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto, User user) {
        List<InviteCard> matchingUser = inviteCardRepository.findByCardId(todoRequestDto.getCardId());
        if(matchingUser.get(0).getUser().getId().equals(user.getId())){
            Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .nickname(todoRequestDto.getNickname())
                .complete(todoRequestDto.isComplete())
                .build();

            Card card = cardRepository.findById(todoRequestDto.getCardId()).orElseThrow(
                () -> new ServiceException(NOT_FOUND_CARD)
            );

            todo.setCard(card);

            Todo savedTodo = todoRepository.save(todo);
            return TodoResponseDto.of(savedTodo);
        }else{
            throw new ServiceException(NOT_EXIST_USER);
        }
    }

    public List<TodoResponseDto> getTodos(Long cardId, User user) {
        List<Todo> byId = todoRepository.findTodoByUserIdWithInvites(cardId,user.getId());

        List<Todo> todos = todoRepository.findAllByUserId(user.getId());

        if (!todos.isEmpty()) {
            return todos.stream()
                .map(TodoResponseDto::of)
                .collect(Collectors.toList());
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_TODO);
        }
    }

    public TodoResponseDto getTodo(Long todoId, User user) {
        Todo todo = getTodo(todoId);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto modifyTodo(Long todoId, TodoRequestDto todoRequestDto, User user) {
        Todo todo = getTodo(todoId);
        var byUser = userRepository.findById(user.getId());

        todo.setContent(todoRequestDto.getContent());
        todo.setComplete(todoRequestDto.isComplete());
        todo.updateModifiedAt(LocalDateTime.now());

        return new TodoResponseDto(todo);
    }

    public void deleteTodo(Long todoId, User user) {
        var byTodo = getTodo(todoId);
        var byUser = userRepository.findById(user.getId());

        todoRepository.delete(byTodo);
    }


    private Todo getTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_FOUND_TODO)
        );
    }


}
