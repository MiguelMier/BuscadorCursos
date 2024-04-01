package es.babel.buscadorcursos.fakebd;

import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.Formador;
import es.babel.buscadorcursos.model.enums.Modalidad;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeBD {

    private List<Curso> listaCursos;
    private List<Alumno> listaAlumnos;
    private Map<Curso, Alumno> alumnosCurso;
    
    public FakeBD(){
        crearAlumnos();
        crearCursos();
        crearMatriculacion();
    }

    private void crearMatriculacion() {
        alumnosCurso = new HashMap<>();
        alumnosCurso.put(listaCursos.get(0),listaAlumnos.get(0));
    }

    private void crearCursos() {
        List<Formador> formadores1 = new ArrayList<>();
        formadores1.add(new Formador("Miguel", "Castañon", "linkedin.com/miguel"));
        formadores1.add(new Formador("Jose", "Antonio", "linkedin.com/chose"));

        List<Formador> formadores2 = new ArrayList<>();
        formadores2.add(new Formador("Viti", "Rozada", "linkedin.com/bala"));
        formadores2.add(new Formador("Jose Angel", "Ziganda", "linkedin.com/kuko"));

        listaCursos = new ArrayList<>();
        listaCursos.add(new Curso("C001", "Introducción a la programación", "Informática", 40,
                Modalidad.PRESENCIAL, 99.99, true, "Calle 123", new Date(), new Date(), formadores1));
        listaCursos.add(new Curso("C002", "Diseño gráfico básico", "Diseño", 30,
                Modalidad.ONLINE, 79.99, false, "Online", new Date(), new Date(), formadores2));


    }

    private void crearAlumnos() {

        listaAlumnos = new ArrayList<>();
        listaAlumnos.add(new Alumno("Borja", "Baston", "12345678A"));
        listaAlumnos.add(new Alumno("María", "González", "87654321B"));
        listaAlumnos.add(new Alumno("Miguel", "Mier", "111333999F"));
        listaAlumnos.add(new Alumno("Eduardo", "Camavinga", "333666999B"));
    }

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public void matricularAlumno(Curso curso, Alumno alumno){
        alumnosCurso.put(curso, alumno);
    }
}
