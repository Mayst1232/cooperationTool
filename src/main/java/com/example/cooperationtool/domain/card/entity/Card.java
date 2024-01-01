package com.example.cooperationtool.domain.card.entity;

import com.example.cooperationtool.domain.column.entity.Columns;
import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Card extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Columns columns;

    @Column(nullable = false)
    @Size(max = 20)
    private String title;

    private LocalDateTime modifiedAt;

    @Column(name = "d_day")
    private Integer dday;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateModifiedAt(LocalDateTime now) {
        this.modifiedAt = now;
    }

    public void setDday(Integer dday) {
        this.dday = dday;
    }

    public void setDueDate(LocalDateTime localDateTime) {
        this.dueDate = localDateTime;
    }
}
