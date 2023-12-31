package com.example.cooperationtool.domain.comment.entity;

import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity {

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Card card;

    @Builder
    public Comment(final Card card, final User user, final String content) {
        this.card = card;
        this.user = user;
        this.content = content;
    }

    public void modifyContent(String content) {
        this.content = content;
    }
}
