package es.babel.buscadorcursos.service;

import es.babel.buscadorcursos.fakebd.FakeBD;
import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;

import java.util.List;

public class CursoService {

    private final FakeBD fakeBD;

    public CursoService(FakeBD fakeBD) {
        this.fakeBD = fakeBD;
    }


    public List<Curso> obtenerTodosCursos() {
        return fakeBD
    }

    public List<Curso> obtenerCursoArea(String curso) {
    }

    public List<Curso> obtenerCursoHora(double hora) {
    }

    public List<Curso> obtenerCursoModalidad(String modalidad) {
    }

    public Curso obtenerCursoID(String id) {
    }

    public void matricularAlumno(Alumno alumno) {
    }
}
