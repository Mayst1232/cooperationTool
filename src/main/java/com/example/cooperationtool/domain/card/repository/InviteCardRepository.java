package com.example.cooperationtool.domain.card.repository;

import com.example.cooperationtool.domain.card.entity.InviteCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteCardRepository extends JpaRepository<InviteCard, Long> {

    void deleteByCardIdAndUserId(Long card_id, Long user_id);

}
