package com.andrea.gestion.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.andrea.gestion.modelos.Usuario;
import com.andrea.gestion.repos.RepoRol;
import com.andrea.gestion.repos.RepoUsuario;




@Controller
@RequestMapping("/usuarios")
public class ControllerUsuario {
    
    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoRol repoRol;

    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<Usuario> lUsuario = repoUsuario.findAll();
        modelo.addAttribute("usuarios", lUsuario);
        return "usuarios/usuarios";
    }

    @GetMapping("")
    public String findAll2(Model modelo) {
        return findAll(modelo);
    }
    
    
    @GetMapping("/add")
    public String addUsuario(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("roles", repoRol.findAll());
        return "usuarios/add";
    }

    @PostMapping("/add")
    public String addUsuario(@ModelAttribute("usuario") @NonNull Usuario usuario) {
        
        repoUsuario.save(usuario);
        
        return "redirect:/usuarios";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> oUsuario = repoUsuario.findById(id);
        if (oUsuario.isPresent())
            modelo.addAttribute(
            "usuario", oUsuario.get());
        else {
            modelo.addAttribute(
                "mensaje", "El usuario consultado no existe.");
            return "error";
        }
        return "usuarios/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable("id") @NonNull Long id) {
        repoUsuario.deleteById(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/edit/{id}")
    public String editUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Usuario> oUsuario = repoUsuario.findById(id);
        if(oUsuario.isPresent()) {
            modelo.addAttribute(
            "usuario", oUsuario.get());
            modelo.addAttribute(
                "roles", repoRol.findAll());
            return "usuarios/edit";
        } else {
            modelo.addAttribute("mensaje", "El usuario consultado no existe.");
            return "error";
        }
    }

}
