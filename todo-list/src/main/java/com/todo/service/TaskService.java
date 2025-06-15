package com.todo.service;

import com.todo.model.Note;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Listar todas as tarefas
    public List<Task> listarTodas() {
        return taskRepository.findAll();
    }

    // Listar tarefas por anotação
    public List<Task> listarPorNote(Note note) {
        // Se quiser, pode criar findByNote no TaskRepository
        return taskRepository.findByNote(note);
    }

    // Buscar por ID
    public Task buscarPorId(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    // Salvar tarefa - obrigatoriamente vinculada a uma anotação
    public Task salvar(Task task) {
        if (task.getNote() == null) {
            throw new IllegalArgumentException("Tarefa deve estar vinculada a uma anotação");
        }
        return taskRepository.save(task);
    }

    // Atualizar tarefa (descrição e status)
    public Task atualizar(Long id, String novaDescricao, boolean novoStatus) {
        Task task = buscarPorId(id);
        task.setDescription(novaDescricao);
        task.setStatus(novoStatus);
        return taskRepository.save(task);
    }

    // Deletar tarefa
    public void deletar(Long id) {
        taskRepository.deleteById(id);
    }
}
