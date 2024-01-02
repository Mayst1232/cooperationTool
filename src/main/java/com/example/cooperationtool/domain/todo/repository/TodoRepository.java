package com.example.cooperationtool.domain.todo.repository;

import com.example.cooperationtool.domain.todo.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findAllByUserId(Long user_id);

    @Query("select t from Todo t "
        + "join InviteCard iv on iv.user.id = t.user.id "
        + "join Card c on c.id = t.card.id "
        + "where iv.user.id = :id and c.id =:cardId ")
    List<Todo> findTodoByUserIdWithInvites(Long cardId, Long id);
}
