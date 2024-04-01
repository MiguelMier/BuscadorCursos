package es.babel.buscadorcursos.service;

import es.babel.buscadorcursos.fakebd.FakeBD;
import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.enums.Modalidad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CursoService implements ICursoService{

    private final FakeBD fakeBD;

    public CursoService(FakeBD fakeBD) {
        this.fakeBD = fakeBD;
    }

    public List<Curso> obtenerTodosCursos() {
        return fakeBD.getListaCursos();
    }

    public List<Curso> obtenerCursoArea(String area) {
        List<Curso> cursosTemp = new ArrayList<>();
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getArea().equals(area)) {
                cursosTemp.add(c);
            }
        }
        return cursosTemp;
    }

    public List<Curso> obtenerCursoHora(double hora) {
        List<Curso> cursosTemp = new ArrayList<>();
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getNumeroHoras() == hora) {
                cursosTemp.add(c);
            }
        }
        return cursosTemp;
    }

    public List<Curso> obtenerCursoModalidad(String modalidad) {
        Modalidad modalidadTemp = null;
        if (modalidad.equalsIgnoreCase("presencial")) {
            modalidadTemp = Modalidad.PRESENCIAL;
        } else if (modalidad.equalsIgnoreCase("online")) {
            modalidadTemp = Modalidad.ONLINE;
        } else if (modalidad.equalsIgnoreCase("mixto")) {
            modalidadTemp = Modalidad.MIXTO;
        } else {
            throw new IllegalArgumentException("Modalidad no válida: " + modalidad);
        }

        List<Curso> cursosTemp = new ArrayList<>();
        for (Curso c : fakeBD.getListaCursos()) {
            if (c.getModalidad().equals(modalidadTemp)) {
                cursosTemp.add(c);
            }
        }
        return cursosTemp;
    }

    public Curso obtenerCursoID(String id) {
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getCursoID().equals(id)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Curso no encontrado: " + id);
    }

    public void matricularAlumno(Alumno alumno, String idCurso) {
        fakeBD.matricularAlumno(obtenerCursoID(idCurso), Objects.requireNonNull(alumno));
    }
}
