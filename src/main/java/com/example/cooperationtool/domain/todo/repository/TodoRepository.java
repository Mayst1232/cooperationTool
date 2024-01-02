package com.example.cooperationtool.domain.todo.repository;

import com.example.cooperationtool.domain.todo.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findAllByUserId(Long user_id);
}
