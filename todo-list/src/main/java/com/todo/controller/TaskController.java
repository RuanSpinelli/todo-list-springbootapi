package com.todo.controller;

import com.todo.model.Note;
import com.todo.model.Task;
import com.todo.service.NoteService;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tarefas")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private NoteService noteService;

    // Lista tarefas de uma nota específica
    @GetMapping("/nota/{noteId}")
    public String listarTarefas(@PathVariable Long noteId, Model model) {
        Note note = noteService.buscarPorId(noteId);
        model.addAttribute("note", note);
        model.addAttribute("tasks", taskService.listarPorNote(note));
        return "tasks/list"; // criar esse template depois
    }

    // Exibe formulário de nova tarefa
    @GetMapping("/nova/{noteId}")
    public String exibirFormularioNovaTarefa(@PathVariable Long noteId, Model model) {
        Note note = noteService.buscarPorId(noteId);
        Task task = new Task();
        task.setNote(note); // já vincula à nota
        model.addAttribute("task", task);
        return "tasks/form"; // criar esse template depois
    }

    // Salva a tarefa
    @PostMapping("/salvar")
    public String salvarTarefa(@ModelAttribute Task task) {
        taskService.salvar(task);
        return "redirect:/tarefas/nota/" + task.getNote().getId();
    }

    // Editar uma tarefa
    @GetMapping("/editar/{id}")
    public String editarTarefa(@PathVariable Long id, Model model) {
        Task task = taskService.buscarPorId(id);
        model.addAttribute("task", task);
        return "tasks/form";
    }

    // Excluir uma tarefa
    @GetMapping("/excluir/{id}")
    public String excluirTarefa(@PathVariable Long id) {
        Long noteId = taskService.buscarPorId(id).getNote().getId();
        taskService.deletar(id);
        return "redirect:/tarefas/nota/" + noteId;
    }
}
