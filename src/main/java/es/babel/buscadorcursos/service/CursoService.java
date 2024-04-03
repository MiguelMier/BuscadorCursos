package es.babel.buscadorcursos.service;

import es.babel.buscadorcursos.fakebd.FakeBD;
import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.DTO.CursoDTO;
import es.babel.buscadorcursos.model.enums.Modalidad;
import es.babel.buscadorcursos.utils.LogUtils;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CursoService implements ICursoService{

    private final FakeBD fakeBD;

    public CursoService(FakeBD fakeBD) {
        this.fakeBD = new FakeBD();
    }

    public List<Curso> obtenerTodosCursos() {
        Long startTime = System.nanoTime();
        List<Curso> cursotemp = fakeBD.getListaCursos();
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        escribirEstadisticasEnArchivo("hola.txt");
        LogUtils.logInfo(" * Tiempo empleado para encontrar todos los cursos: " + totalTime);
        LogUtils.logTrace(" - Listado de cursos obtenidos");
        return cursotemp;
    }

    public List<Curso> obtenerCursoArea(String area) {
        Long startTime = System.nanoTime();
        List<Curso> cursosTemp = new ArrayList<>();
        for(Curso c: fakeBD.getListaCursos()){
            if(c.getArea().equalsIgnoreCase(area)) {
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

        Modalidad modalidadTemp = checkModalidad(modalidad);

        List<Curso> cursosTemp = getCursoByModalidad(modalidadTemp);
        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        LogUtils.logInfo(" * Tiempo empleado para encontrar los cursos de la modalidad " + modalidad +": " + totalTime);
        LogUtils.logTrace(" - Se han encontrado " + cursosTemp.size() + " cursos de modalidad: " + modalidad);

        return cursosTemp;
    }

    public CursoDTO obtenerCursoDTOID(String id) {
        return fakeBD.getCursoDTOId(id);
    }

    public void escribirEstadisticasEnArchivo(String nombreFichero) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String nombreArchivoConFecha = sdf.format(new Date()) + " - " + nombreFichero;
        try (FileWriter writer = new FileWriter(nombreArchivoConFecha)) {
            double promedioHoras = calcularPromedioHoras();
            int cantidadCursos = fakeBD.getListaCursos().size();

            writer.write("Estadísticas relevantes:");
            writer.write("Cantidad de cursos: " + cantidadCursos);
            writer.write("Promedio de horas por curso: " + promedioHoras);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private double calcularPromedioHoras() {
        if (fakeBD.getListaCursos().isEmpty()) {
            return 0.0;
        }
        double totalHoras = 0;
        for (Curso curso : fakeBD.getListaCursos()) {
            totalHoras += curso.getNumeroHoras();
        }
        return totalHoras / fakeBD.getListaCursos().size();
    }

    public void matricularAlumno(Alumno alumno, String idCurso) {
        if (idCurso == null || idCurso.isEmpty()) {
            throw new IllegalArgumentException("El ID del curso no puede ser nulo o vacío");
        }
        if (alumno == null) {
            throw new IllegalArgumentException("El alumno no puede ser nulo");
        }

        Long startTime = System.nanoTime();

        fakeBD.matricularAlumno(obtenerCursoID(idCurso), alumno);

        Long endTime = System.nanoTime();
        Long totalTime = endTime - startTime;
        LogUtils.logTrace(" - Se ha matriculado al alumno: " + alumno.getNombre() + " en el curso: " + idCurso);
        LogUtils.logInfo(" * Tiempo empleado para matricular al alumno " + alumno.getNombre() +": " + totalTime);
    }

    public Map<Curso, Alumno> getMatriculados(){
        return fakeBD.getAlumnosCurso();
    }

    // MÉTODOS PRIVADOS

    private Curso obtenerCursoID(String id) {
        return fakeBD.getCursoId(id);
    }

    private Modalidad checkModalidad(String modalidad) {
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
        return modalidadTemp;
    }

    private List<Curso> getCursoByModalidad(Modalidad modalidadTemp) {
        List<Curso> cursosTemp = new ArrayList<>();
        for (Curso c : fakeBD.getListaCursos()) {
            if (c.getModalidad().equals(modalidadTemp)) {
                cursosTemp.add(c);
            }
        }
        return cursosTemp;
    }
}
