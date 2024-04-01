package es.babel.buscadorcursos.controller;

import es.babel.buscadorcursos.fakebd.FakeBD;
import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.DTO.CursoDTO;
import es.babel.buscadorcursos.service.CursoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private CursoService cursoService;
    private final FakeBD fakeBD;

    public CursoController(){

        this.fakeBD = new FakeBD();
        this.cursoService = new CursoService(fakeBD);
    }

    @GetMapping
    public List<Curso> obtenerTodosCursos(){
        return cursoService.obtenerTodosCursos();
    }

    @GetMapping("/area/{area}")
    public List<Curso> obtenerCursoArea(@RequestBody String curso){
        return cursoService.obtenerCursoArea(curso);
    }

    @GetMapping("/hora/{hora}")
    public List<Curso> obtenerCursoHora(@RequestBody double hora){
        return cursoService.obtenerCursoHora(hora);
    }

    @GetMapping("/modalidad/{modalidad}")
    public List<Curso> obtenerCursoModalidad(@RequestBody String modalidad){
        return cursoService.obtenerCursoModalidad(modalidad);
    }

    @GetMapping("/{id}")
    public CursoDTO obtenerCursoID(@RequestBody String id){
        return cursoService.obtenerCursoDTOID(id);
    }

    @PostMapping("/matricular")
    public void matricularAlumno(@RequestBody Alumno alumno, String idCurso){
        cursoService.matricularAlumno(alumno, idCurso);
    }

}
