package com.todo.service;

import com.todo.model.Note;
import com.todo.model.Usuario;
import com.todo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    // Puxa o repositório para poder ter acesso e manipular os dados
    @Autowired
    private NoteRepository noteRepository;

    // Listar por usuário
    public List<Note> listarPorUsuario(Usuario usuario) {
        return noteRepository.findByOwner(usuario);
    }

    // Puxar todas as notas com base no usuário
    public List<Note> findAllByUsuario(Usuario usuario) {

        return noteRepository.findAllByOwner(usuario);
    }

    // Listar todas as anotações
    public List<Note> listarTodas() {
        
        return noteRepository.findAll();
    }

    // Serve para checar se a nota pertence ao usuário que está tentando manipular
    public Note buscarNotaDoUsuario(Long notaId, Usuario usuario) {
        Note note = buscarPorId(notaId);
        if (!note.getOwner().getId().equals(usuario.getId())) {
            throw new RuntimeException("Você não tem permissão para acessar esta nota.");
        }
        return note;
    }


    // Buscar por Id
    public Note buscarPorId(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));
    }

    // Salvar as anotações
    public Note salvar(Note note) {
        return noteRepository.save(note);
    }

    // Atualizar a anotação
    public Note atualizar(Long id, String novoTitulo, String novaDescricao) {
        Note note = buscarPorId(id);
        note.setTitle(novoTitulo);
        note.setDescription(novaDescricao);
        return noteRepository.save(note);
    }

    // Deletar uma anotação
    public void deletar(Long id){
        noteRepository.deleteById(id);
    }


}
