package com.example.cooperationtool.domain.board.repository;

import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.entity.InviteBoard;
import com.example.cooperationtool.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteBoardRepository extends JpaRepository<InviteBoard, Long> {
    Optional<InviteBoard> findByUserAndBoard(User checkUser, Board checkBoard);

    List<InviteBoard> findAllByUser_Id(Long id);
}
