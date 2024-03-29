package com.example.crud.controller;


import com.example.crud.usuarios.UsuariosResponseDTO;
import com.example.crud.usuarios.UsuariosRequestDTO;
import com.example.crud.usuarios.Usuarios;
import com.example.crud.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class CrudController {
    @Autowired // para injetar essa dependência na classe
    private UsuariosRepository repository;
    @CrossOrigin(origins = "*", allowedHeaders = "*") // para conexão com o front
    @PostMapping()
    public void saveUsuario(@RequestBody UsuariosRequestDTO data){
        // pq? repository espera uma entidade Usuarios, não a DTO
        Usuarios usuariosData = new Usuarios(data);
        repository.save(usuariosData);
        return;

    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping()
    public List<UsuariosResponseDTO> getAll(@RequestParam(required = false) String name) {
        if (name != null) {
            // Filtra os usuários cujo nome contenha a substring especificada
            return repository.findByNameContainingIgnoreCase(name).stream().map(UsuariosResponseDTO::new).toList();
        } else {
            // Retorna todos os usuários se nenhum filtro for fornecido
            return repository.findAll().stream().map(UsuariosResponseDTO::new).toList();
        }
    }
}

