package com.example.cooperationtool.domain.card.repository;

import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.entity.InviteCard;
import com.example.cooperationtool.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InviteCardRepository extends JpaRepository<InviteCard, Long> {

    void deleteByCardIdAndUserId(Card cardId, User userId);

}
