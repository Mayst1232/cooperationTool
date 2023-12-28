package com.example.cooperationtool.domain.card.repository;

import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findByIdAndUser(Long cardId, User user);
}
