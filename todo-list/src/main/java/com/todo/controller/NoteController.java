package com.todo.controller;


import com.todo.model.Note;
import com.todo.model.Usuario;
import com.todo.service.NoteService;
import com.todo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notas")
public class NoteController {


    @Autowired
    private NoteService notaService;

    @Autowired
    private UsuarioService usuarioService;

    // Apresenta a lista de notas disponíveis
    @GetMapping
    public String listarNotas(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String nome = userDetails.getUsername();
        Usuario usuario = usuarioService.findByName(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("notes", notaService.listarPorUsuario(usuario));
        return "notes/list"; // nome da view JSP ou Thymeleaf que você vai criar
    }


    @GetMapping("/nova")
    public String exibirFormularioNovaNot(Model model) {
        model.addAttribute("note", new Note());
        return "#"; // leva pro formulário de nova nota
    }


    @PostMapping("/salvar")
    public String salvarNota(@ModelAttribute Note note, @AuthenticationPrincipal UserDetails userDetails) {
        String nome = userDetails.getUsername();
        Usuario usuario = usuarioService.findByName(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        note.setOwner(usuario);
        notaService.salvar(note);
        return "redirect:/notas"; // para voltar à lista após salvar
    }

    // Busca a nota e mostra para o usuário poder mexer
    @GetMapping("/editar/{id}")
    public String editarNota(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Note note = notaService.buscarPorId(id);

        // Verificar se a nota pertence ao usuário logado
        String nome = userDetails.getUsername();
        Usuario usuario = usuarioService.findByName(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!note.getOwner().getId().equals(usuario.getId())) {
            throw new RuntimeException("Você não tem permissão para editar essa nota");
        }

        model.addAttribute("note", note);
        return "notes/form";
    }

    // Recebe os dados modificados e grava no banco
    @PostMapping("/atualizar/{id}")
    public String atualizarNota(@PathVariable Long id, @ModelAttribute Note note, @AuthenticationPrincipal UserDetails userDetails) {
        String nome = userDetails.getUsername();
        Usuario usuario = usuarioService.findByName(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Note notaExistente = notaService.buscarPorId(id);

        if (!notaExistente.getOwner().getId().equals(usuario.getId())) {
            throw new RuntimeException("Você não tem permissão para atualizar essa nota");
        }

        // Atualiza título e descrição
        notaExistente.setTitle(note.getTitle());
        notaExistente.setDescription(note.getDescription());

        notaService.salvar(notaExistente);

        return "redirect:/notas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirNota(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Note note = notaService.buscarPorId(id);

        String nome = userDetails.getUsername();
        Usuario usuario = usuarioService.findByName(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!note.getOwner().getId().equals(usuario.getId())) {
            throw new RuntimeException("Você não tem permissão para excluir essa nota");
        }

        notaService.deletar(id);

        return "redirect:/notas";
    }

}
