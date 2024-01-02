package com.example.cooperationtool.domain.card.repository;

import com.example.cooperationtool.domain.card.entity.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    @Modifying
    @Query("update Card c set c.priority = c.priority + :moveDirection "
        + "where c.priority between :start and :end")
    void moveCard(Long start, Long end, Long moveDirection);


    @Query("select c from Card as c where c.columns.id = :id "
        + "order by c.priority asc")
    List<Card> findAllOrderByPriority(Long id);

    @Query("SELECT c FROM Card c "
        + "JOIN c.inviteCard ic "
        + "WHERE ic.user.id =: userId OR ic.user.id =:userId "
        + "order by ic.id asc")
    List<Card> findCardsByUserIdWithInvites(Long userId);

    @Query("select c from Card c "
        + "join c.inviteCard ic "
        + "where c.id =: cardId "
        + "and c.user.id =: userId and ic.user.id =: userId")
    Optional<Card> findBycardAndUserIdWithInvites(Long cardId, Long userId);
}
