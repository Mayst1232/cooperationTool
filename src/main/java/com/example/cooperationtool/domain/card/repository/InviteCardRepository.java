package com.example.cooperationtool.domain.card.repository;

import com.example.cooperationtool.domain.card.entity.InviteCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteCardRepository extends JpaRepository<InviteCard, Long> {

    void deleteByCardIdAndUserId(Long card_id, Long user_id);

    List<InviteCard> findByCardId(Long cardId);

}
