package com.andrea.gestion.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrea.gestion.modelos.Matricula;

public interface RepoMatricula extends JpaRepository<Matricula,Long>{
    
}
