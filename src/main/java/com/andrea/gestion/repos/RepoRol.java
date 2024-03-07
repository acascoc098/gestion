package com.andrea.gestion.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrea.gestion.modelos.Rol;

public interface RepoRol extends JpaRepository<Rol,Long>{
    
}
