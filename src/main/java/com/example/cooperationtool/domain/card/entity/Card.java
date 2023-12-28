package com.example.cooperationtool.domain.card.entity;

import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(nullable = false)
    @Size(max = 20)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private LocalDateTime modifiedAt;

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateModifiedAt(LocalDateTime now) {
        this.modifiedAt = now;
    }
}
