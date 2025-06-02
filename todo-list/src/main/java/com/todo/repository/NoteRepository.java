package com.todo.repository;

import com.todo.model.Note;
import com.todo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByOwner(Usuario usuario);
    
}
