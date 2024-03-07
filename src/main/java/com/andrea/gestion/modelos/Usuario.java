package com.andrea.gestion.modelos;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    private String apellido;
    private boolean enabled;
    @Column(length = 100)
    private String email;
    @Column(length = 25)
    private String username;
    @Column(length = 100)
    private String password;
    @Column(length = 9)
    private int numero;
    @ManyToMany
    private List<Rol> roles;
}
