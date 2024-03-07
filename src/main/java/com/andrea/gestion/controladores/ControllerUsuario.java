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
import org.springframework.web.bind.annotation.RequestParam;

import com.andrea.gestion.modelos.Asignatura;
import com.andrea.gestion.modelos.Matricula;
import com.andrea.gestion.modelos.Usuario;
import com.andrea.gestion.repos.RepoAsignatura;
import com.andrea.gestion.repos.RepoMatricula;
import com.andrea.gestion.repos.RepoRol;
import com.andrea.gestion.repos.RepoUsuario;




@Controller
@RequestMapping("/usuarios")
public class ControllerUsuario {
    
    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoMatricula repoMatricula;

    @Autowired
    private RepoAsignatura repoAsignatura;

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
        modelo.addAttribute("matriculas", repoMatricula.findAll());
        modelo.addAttribute("roles", repoRol.findAll());
        return "usuarios/add";
    }

    @PostMapping("/add")
    public String addUsuario(@ModelAttribute("usuario") @NonNull Usuario usuario) {
        
        List<Matricula> matriculas = usuario.getMatriculas();
        if(matriculas!=null){
            for (Matricula matricula : matriculas) {
                repoMatricula.save(matricula);
            }
        }
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

    @GetMapping("/{id}/matriculas/add")
    public String matriculaAddForm(
        @PathVariable Long id,
        Model modelo) {
        
        Optional<Usuario> oUsuario = repoUsuario.findById(id);

        if (!oUsuario.isPresent()) {
            modelo.addAttribute(
                "mensaje", "El usuario no existe");
            return "error";
        }

        Optional<Asignatura> oAsignatura = repoAsignatura.findById(repoAsignatura.count());

    
        modelo.addAttribute(
             "asignatura_actual", oAsignatura.get());
        modelo.addAttribute(
            "asignaturas", repoAsignatura.findAll());
        modelo.addAttribute(
            "matricula", new Matricula());
        
        return "usuarios/matriculas/add";
    }
 
    @PostMapping("/{id}/matriculas/add")
    public String matriculaAdd(
        @PathVariable @NonNull Long id,
        @ModelAttribute("matricula") Matricula matricula, 
        @RequestParam("asignaturaId") Long asignaturaId,  
        Model modelo) {

        Optional<Usuario> oUsuario = repoUsuario.findById(id);
        Optional<Asignatura> oAsignatura = repoAsignatura.findById(asignaturaId);

        if (!oUsuario.isPresent() || !oAsignatura.isPresent()) {
            modelo.addAttribute("mensaje", "El usuario o la asignatura no existe");
            return "error";
        }

        if (repoMatricula.existsByUsuarioAndAsignatura(oUsuario.get(), oAsignatura.get())) {
            modelo.addAttribute("mensaje", "El usuario ya está matriculado en esta asignatura");
            return "error";
        }

        Matricula nuevaMatricula = new Matricula();
        nuevaMatricula.setUsuario(oUsuario.get());
        nuevaMatricula.setAsignatura(oAsignatura.get());
        repoMatricula.save(nuevaMatricula);

        return "redirect:/usuarios";
    }
    
    @GetMapping("/{id}/matriculas")
    public String telefonos(
        @PathVariable @NonNull Long id,
        Model modelo) {
        
        Optional<Usuario> oUsuario = repoUsuario.findById(id);

        if (!oUsuario.isPresent()) {
            modelo.addAttribute(
                "mensaje", "El usuario no existe");
            return "error";
        }

        modelo.addAttribute(
            "usuarios", repoUsuario.findAll());
        modelo.addAttribute(
            "usuarioActual", oUsuario.get());
        modelo.addAttribute(
            "matriculas", oUsuario.get().getMatriculas());

        modelo.addAttribute(
            "matricula", new Matricula());
        
        return "usuarios/matriculas/matriculas";
    }

    @GetMapping("/matriculas/delete/{id}")
    public String deleteMatricula(
            @PathVariable("id") @NonNull Long id) {
        try {
            repoMatricula.deleteById(id);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/usuarios";
    }
}
