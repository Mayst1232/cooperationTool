package com.example.cooperationtool.domain.card.repository;

import com.example.cooperationtool.domain.card.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Modifying
    @Query("update Card c set c.priority = c.priority + :moveDirection "
        + "where c.priority between :start and :end")
    void moveCard(Long start, Long end, Long moveDirection);


    @Query("select c from Card as c where c.columns.id = :id "
        + "order by c.priority asc")
    List<Card> findAllOrderByPriority(Long id);

    @Query("select distinct c from Card c "
        + "join Columns cl on cl.id = c.columns.id "
        + "join Board b on b.id = cl.board.id "
        + "join InviteBoard ib on ib.board.id = b.id "
        + "where ib.board.id = :boardId and ib.user.id = :userId "
        + "order by c.priority asc")
    List<Card> findCardsByUserIdWithInvites(Long boardId, Long userId);

}