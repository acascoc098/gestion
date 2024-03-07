package com.andrea.gestion.controladores;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.andrea.gestion.modelos.Asignatura;
import com.andrea.gestion.modelos.Matricula;
import com.andrea.gestion.repos.RepoAsignatura;
import com.andrea.gestion.repos.RepoMatricula;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/asignaturas")
public class ControllerAsignatura {
    @Autowired
    RepoAsignatura repoAsignatura;

    @Autowired
    RepoMatricula repoMatricula;

    @GetMapping("")
    public String findAll(Model modelo) {
        modelo.addAttribute(
            "asignaturas", 
            repoAsignatura.findAll());
        return "asignaturas/asignaturas";
    }
    
    @GetMapping("/add")
    public String addForm(Model modelo) {
        modelo.addAttribute("asignaturas", repoAsignatura.findAll());        
        return "asignaturas/add";
    }

    @PostMapping("/add")
    public String postMethodName(
        @ModelAttribute("asignatura") Asignatura asignatura)  {
        repoAsignatura.save(asignatura);
        return "redirect:/asignaturas";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteForm(
            @PathVariable(name = "id") @NonNull Long id,
            Model modelo) {
        try {
            Optional<Asignatura> asignatura = repoAsignatura.findById(id);
            if (asignatura.isPresent()){
                modelo.addAttribute(
                    "asignatura", asignatura.get());
                return "asignaturas/delete";
            } else {
                return "error";
            }

        } catch (Exception e) {
            return "error";
        }
    }
    

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") @NonNull Long id) {
        try {
            Optional<Asignatura> asignaturaOptional = repoAsignatura.findById(id);

            if (asignaturaOptional.isPresent()) {
                Asignatura asignatura = asignaturaOptional.get();
                List<Matricula> matriculas = asignatura.getMatriculas();
                for (Matricula matricula : matriculas) {
                    repoMatricula.deleteById(matricula.getId());
                }
                repoAsignatura.deleteById(id);
            } else {
                return "error"; 
            }

        } catch (Exception e) {
            return "error";
        }

        return "redirect:/asignaturas";
    }


    @GetMapping("/edit/{id}")
    public String editForm(
        @PathVariable @NonNull Long id,
        Model modelo) {
        Optional<Asignatura> asignatura = repoAsignatura.findById(id);
        List<Asignatura> asignaturas = repoAsignatura.findAll();
        if (asignatura.isPresent()){
            Asignatura asignaturaEntity = asignatura.get();
            List<Matricula> matriculas = asignaturaEntity.getMatriculas();
            for (Matricula matricula : matriculas) {
                repoMatricula.deleteById(matricula.getId());
            }

            modelo.addAttribute("asignatura", asignaturaEntity);
            modelo.addAttribute("asignaturas", asignaturas);
            return "asignaturas/edit";
        } else {
            modelo.addAttribute("mensaje", "Asignatura no encontrada");
            return "error";
        }
    }


    @GetMapping("/{id}/matriculas")
    public String matriculas(
        @PathVariable Long id,
        Model modelo) {
        
        Optional<Asignatura> oAsignatura = repoAsignatura.findById(id);

        if (!oAsignatura.isPresent()) {
            modelo.addAttribute(
                "mensaje", "La asignatura no existe");
            return "error";
        }

        modelo.addAttribute(
            "asignaturas", repoAsignatura.findAll());
        modelo.addAttribute(
             "asignaturaActual", oAsignatura.get());
        modelo.addAttribute(
             "matriculas", oAsignatura.get().getMatriculas());

        modelo.addAttribute(
            "matricula", new Matricula());
        
        return "asignaturas/matriculas/matriculas";
    }
    
    @GetMapping("/matriculas/delete/{id}")
    public String deleteMatricula(
            @PathVariable("id") @NonNull Long id) {
        try {
            repoMatricula.deleteById(id);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/asignaturas";
    }
    
}
