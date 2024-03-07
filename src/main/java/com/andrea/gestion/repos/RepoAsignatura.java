package com.andrea.gestion.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrea.gestion.modelos.Asignatura;

public interface RepoAsignatura extends JpaRepository<Asignatura,Long>{
    
}
