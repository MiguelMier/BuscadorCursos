package es.babel.buscadorcursos.service;

import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.DTO.CursoDTO;

import java.util.List;

public interface ICursoService {

    CursoDTO obtenerCursoDTOID(String id);
    List<Curso> obtenerCursoModalidad(String modalidad);
    List<Curso> obtenerCursoHora(double hora);
    List<Curso> obtenerCursoArea(String area);
    List<Curso> obtenerTodosCursos();
    void matricularAlumno(Alumno alumno, String idCurso);

}
