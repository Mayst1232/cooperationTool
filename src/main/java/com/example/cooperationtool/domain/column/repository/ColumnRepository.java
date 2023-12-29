package com.example.cooperationtool.domain.column.repository;

import com.example.cooperationtool.domain.column.entity.Columns;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<Columns, Long> {

    @Query("select count(*) from Columns c "
        + "where c.board.id = :boardId")
    Long countByBoardId(Long boardId);

    @Modifying
    @Query("update Columns c set c.priority = c.priority + :moveDirection"
        + " where c.priority between :wantPriority and :endPriority")
    void moveColumn(Long wantPriority, Long endPriority, Long moveDirection);

    @Query("select c from Columns as c where c.board.id = :id "
        + "order by c.priority asc")
    List<Columns> findAllOrderByPriority(Long id);
}
