package com.example.cooperationtool.domain.column.entity;

import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "columns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Columns extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "columns", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> card;

    @Builder
    private Columns(String title, Long priority, User user, Board board) {
        this.title = title;
        this.priority = priority;
        this.user = user;
        this.board = board;
    }

    public void move(Long wantPriority) {
        this.priority = wantPriority;
    }
}
