package com.example.cooperationtool.domain.column.entity;

import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String name;

    @Column(nullable = false)
    private int sort;

    public void sortUp(){
        this.sort = Math.max(0, this.sort - 1);

    }

    public void sortDown(){
        this.sort = this.sort + 1;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    private Columns(String name, int sort, User user, Board board){
        this.name = name;
        this.sort = sort;
        this.user = user;
        this.board = board;
    }

}
