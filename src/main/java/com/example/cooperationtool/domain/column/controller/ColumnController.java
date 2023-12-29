package com.example.cooperationtool.domain.column.controller;

import com.example.cooperationtool.domain.column.dto.ColumnRequestDto;
import com.example.cooperationtool.domain.column.dto.ColumnResponseDto;
import com.example.cooperationtool.domain.column.dto.MoveColumnRequestDto;
import com.example.cooperationtool.domain.column.service.ColumnService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/columns")
    public ResponseEntity<?> createColumn(
        @RequestBody ColumnRequestDto columnRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnResponseDto columnReposeDto = columnService.createColumn(columnRequestDto,
            userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("201")
            .message("컬럼 생성 성공")
            .data(columnReposeDto)
            .build());
    }

    @GetMapping("/columns")
    public ResponseEntity<List<ColumnResponseDto>> getAllColumn() {
        List<ColumnResponseDto> columns = columnService.getAllColumns();
        return ResponseEntity.ok(columns);
    }

    @GetMapping("/columns/{columnId}")
    public ResponseEntity<ColumnResponseDto> getColumnById(@PathVariable Long columnId) {
        ColumnResponseDto column = columnService.getColumnById(columnId);
        return ResponseEntity.ok(column);
    }


    @PatchMapping("/columns/{columnId}")
    public ResponseEntity<?> updateColumnName(
        @PathVariable Long columnId,
        @RequestParam String title) {
        ColumnResponseDto responsedto = columnService.updateColumnName(columnId, title);
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("컬럼 이름 수정 성공")
            .data(responsedto)
            .build());
    }

    @DeleteMapping("/columns/{columnId}")
    public ResponseEntity<?> deleteColumn(
        @PathVariable Long columnId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnService.deleteColumn(columnId, userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("컬럼 삭제 성공")
            .build());
    }


    @PutMapping("/columns/{columnId}")
    public ResponseEntity<?> moveColumn(
        @PathVariable(name = "columnId") Long columnId,
        @RequestBody MoveColumnRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<ColumnResponseDto> responseDtoList = columnService.moveColumn(columnId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("컬럼 이동 성공")
            .data(responseDtoList)
            .build());
    }
}
