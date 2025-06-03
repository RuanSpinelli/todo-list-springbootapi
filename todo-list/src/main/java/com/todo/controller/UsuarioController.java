package com.todo.controller;

import com.todo.model.Usuario;
import com.todo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Mostra o formulário de cadastro
    @GetMapping("/cadastrar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/cadastro"; // Vai procurar templates/usuarios/cadastro.html
    }

    // Recebe os dados do formulário e cadastra o usuário
    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.cadastrar(usuario.getName(), usuario.getPassword());
        return "redirect:/usuarios/cadastrar";
    }


/*
// Mostra todos os usuários cadastrados
    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista"; // Vai procurar templates/usuarios/lista.html
    }
 */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public String listarUsuarios(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        System.out.println(userDetails.getUsername());
        model.addAttribute("usuarioLogado", userDetails.getUsername()); // Passa o nome do usuário autenticado
        return "usuarios/lista";
    }


}
