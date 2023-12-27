package com.example.cooperationtool.domain.board.repository;

import com.example.cooperationtool.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
