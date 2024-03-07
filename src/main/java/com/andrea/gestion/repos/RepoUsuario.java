package com.andrea.gestion.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrea.gestion.modelos.Usuario;

public interface RepoUsuario extends JpaRepository<Usuario,Long>{
    
}
