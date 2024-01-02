package com.example.cooperationtool.domain.todo.entity;

import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "todos")
@Builder
@AllArgsConstructor
public class Todo extends BaseEntity {

    @Column
    private String content;

    @Column
    private boolean complete;

    @Column
    private String nickname;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateModifiedAt(LocalDateTime now){
        this.modifiedAt = now;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
