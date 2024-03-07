package com.andrea.gestion.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrea.gestion.modelos.Asignatura;
import com.andrea.gestion.modelos.Matricula;
import com.andrea.gestion.modelos.Usuario;

public interface RepoMatricula extends JpaRepository<Matricula,Long>{
    boolean existsByUsuarioAndAsignatura(Usuario usuario, Asignatura asignatura);
}
