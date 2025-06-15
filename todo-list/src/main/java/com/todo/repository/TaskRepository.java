package com.todo.repository;

import com.todo.model.Note;
import com.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByNote(Note note);

}
