package es.babel.buscadorcursos.service;

import es.babel.buscadorcursos.fakebd.FakeBD;
import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.DTO.CursoDTO;
import es.babel.buscadorcursos.model.enums.Modalidad;
import es.babel.buscadorcursos.utils.LogUtils;
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
        Long startTime = System.nanoTime();
        List<Curso> cursotemp = fakeBD.getListaCursos();
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        LogUtils.logInfo(" * Tiempo empleado para encontrar todos los cursos: " + totalTime);
        LogUtils.logTrace(" - Listado de cursos obtenidos");
        return cursotemp;
    }

    public List<Curso> obtenerCursoArea(String area) {
        Long startTime = System.nanoTime();
        List<Curso> cursosTemp = new ArrayList<>();
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getArea().equals(area)) {
                cursosTemp.add(c);
            }
        }
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        if(cursosTemp.size()==0){
            LogUtils.logEror(" - No hay cursos con este area: " + area);
            throw new IllegalArgumentException("Area sin cursos: " + area);
        }
        else{
            LogUtils.logInfo(" * Tiempo empleado para encontrar los cursos del area " + area +": " + totalTime);
            LogUtils.logTrace(" - Se han encontrado " + cursosTemp.size() + " cursos del area: " + area);
            return cursosTemp;
        }
    }

    public List<Curso> obtenerCursoHora(double hora) {
        Long startTime = System.nanoTime();
        List<Curso> cursosTemp = new ArrayList<>();
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getNumeroHoras() == hora) {
                cursosTemp.add(c);
            }
        }
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        LogUtils.logInfo(" * Tiempo empleado para encontrar los cursos de duración " + hora +" horas: " + totalTime);
        LogUtils.logTrace(" - Se han encontrado " + cursosTemp.size() + " cursos de duración: " + hora + " horas");
        return cursosTemp;
    }

    public List<Curso> obtenerCursoModalidad(String modalidad) {
        Long startTime = System.nanoTime();

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
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        LogUtils.logInfo(" * Tiempo empleado para encontrar los cursos de la modalidad " + modalidad +": " + totalTime);
        LogUtils.logTrace(" - Se han encontrado " + cursosTemp.size() + " cursos de modalidad: " + modalidad);

        return cursosTemp;
    }

    public Curso obtenerCursoIDv1(String id) {
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getCursoID().equals(id)) {
                LogUtils.logTrace(" - Curso encontrado: ID: " + id + " nombre: " + c.getNombreCurso());
                return c;
            }
        }
        throw new IllegalArgumentException("Curso no encontrado: " + id);

    }

    public CursoDTO obtenerCursoDTOID(String id) {
        return fakeBD.getCursoDTOId(id);
    }

    private Curso obtenerCursoID(String id) {
        return fakeBD.getCursoId(id);
    }

    public void matricularAlumno(Alumno alumno, String idCurso) {
        Long startTime = System.nanoTime();

        LogUtils.logTrace(" - Se ha matriculado al alumno: " + alumno.getNombre() + " en el curso: " + idCurso);
        fakeBD.matricularAlumno(obtenerCursoID(idCurso), Objects.requireNonNull(alumno));
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        LogUtils.logInfo(" * Tiempo empleado para matricular al alumno " + alumno.getNombre() +": " + totalTime);
    }
}
