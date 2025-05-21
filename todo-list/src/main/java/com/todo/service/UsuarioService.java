package com.todo.service;

import com.todo.model.Role;
import com.todo.model.Usuario;
import com.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrar (String nome, String senha) {
        Usuario usuario = new Usuario();
        usuario.setName(nome);
        usuario.setPassword(passwordEncoder.encode(senha));
        usuario.setRole(Role.USER);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByName(nome);
    }


}
