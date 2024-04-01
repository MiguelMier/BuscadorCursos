package es.babel.buscadorcursos.controller;

import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.service.CursoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("curso/")
public class CursoController {

    private CursoService cursoService;

    @GetMapping
    public List<Curso> obtenerTodosCursos(){
        return cursoService.obtenerTodosCursos();
    }

    @GetMapping("/{area}")
    public List<Curso> obtenerCursoArea(@RequestBody String curso){
        return cursoService.obtenerCursoArea(curso);
    }

    @GetMapping("/{hora}")
    public List<Curso> obtenerCursoHora(@RequestBody double hora){
        return cursoService.obtenerCursoHora(hora);
    }

    @GetMapping("/{modalidad}")
    public List<Curso> obtenerCursoModalidad(@RequestBody String modalidad){
        return cursoService.obtenerCursoModalidad(modalidad);
    }

    @GetMapping("/{id}")
    public Curso obtenerCursoID(@RequestBody String id){
        return cursoService.obtenerCursoID(id);
    }

    @PostMapping("/matricular")
    public void matricularAlumno(@RequestBody Alumno alumno){
        cursoService.matricularAlumno(alumno);
    }

}
