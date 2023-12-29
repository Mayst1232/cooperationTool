package com.example.cooperationtool.domain.column.repository;

import com.example.cooperationtool.domain.column.entity.Columns;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<Columns, Long> {
        Optional<Columns> findByName(String name);
        @Query
        List<Columns> findByBoardIdOrderByOrderAsc(Long boardId);
}
