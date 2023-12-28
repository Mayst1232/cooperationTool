package com.example.cooperationtool.domain.column.repository;

import com.example.cooperationtool.domain.column.entity.Column;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column, Long> {
        Optional<Column> findByName(String name);
        List<Column> findByBoardIdOrderByOrderAsc(Long boardId);
}
