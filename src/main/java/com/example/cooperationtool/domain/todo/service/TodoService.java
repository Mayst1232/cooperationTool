package com.example.cooperationtool.domain.todo.service;

import com.example.cooperationtool.domain.card.entity.Card;
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
import java.util.Optional;
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
        var inviteCard = inviteCardRepository.findByUserId(user.getId());
        var byUser = userRepository.findById(user.getId());
        var byCard = cardRepository.findById(todoRequestDto.getCardId());

        if (todoRequestDto.getCardId() > 0 && byUser.isPresent()) {

            Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .nickname(todoRequestDto.getNickname())
                .complete(todoRequestDto.isComplete())
                .build();

            Todo savedTodo = todoRepository.save(todo);

            return TodoResponseDto.of(savedTodo);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    public List<TodoResponseDto> getTodos(User user) {
        List<Todo> todos = todoRepository.findAllByUserId(user.getId());
        if(!todos.isEmpty()){
            return todos.stream()
                .map(TodoResponseDto::of)
                .collect(Collectors.toList());
        }else{
            throw new ServiceException(ErrorCode.NOT_FOUND_TODO);
        }
    }

    public TodoResponseDto getTodo(Long todoId, User user) {
        Todo todo = getTodo(todoId);

        Optional<User> byUser = userRepository.findById(user.getId());

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
        Todo todo = todoRepository.findById(todoId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_FOUND_TODO)
        );
        return todo;
    }


}
