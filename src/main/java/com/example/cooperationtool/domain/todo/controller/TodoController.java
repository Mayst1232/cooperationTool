package com.example.cooperationtool.domain.todo.controller;

import com.example.cooperationtool.domain.todo.dto.TodoModifyRequestDto;
import com.example.cooperationtool.domain.todo.dto.TodoRequestDto;
import com.example.cooperationtool.domain.todo.dto.TodoResponseDto;
import com.example.cooperationtool.domain.todo.service.TodoService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoRequestDto todoRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.createTodo(todoRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("201")
            .message("성공적으로 생성되었습니다.")
            .data(responseDto)
            .build());
    }

    @GetMapping
    public ResponseEntity<List<?>> getTodos(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam Long cardId) {
        List<TodoResponseDto> todoResponseDto = todoService.getTodos(cardId, userDetails.getUser());
        return ResponseEntity.ok().body(Collections.singletonList(RootResponseDto.builder()
            .code("200")
            .message("전체 조회 완료")
            .data(todoResponseDto)
            .build()));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<?> modifyTodo(@PathVariable Long todoId,
        @RequestBody TodoModifyRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDto todoResponseDto = todoService.modifyTodo(todoId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("201")
            .message("수정 완료")
            .data(todoResponseDto)
            .build());
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(todoId, userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("201")
            .message("삭제 완료")
            .build());
    }

}
