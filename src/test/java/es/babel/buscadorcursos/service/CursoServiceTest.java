package es.babel.buscadorcursos.service;

import es.babel.buscadorcursos.fakebd.FakeBD;
import es.babel.buscadorcursos.model.Alumno;
import es.babel.buscadorcursos.model.Curso;
import es.babel.buscadorcursos.model.DTO.CursoDTO;
import es.babel.buscadorcursos.model.Formador;
import es.babel.buscadorcursos.model.enums.Modalidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CursoServiceTest {

    private CursoService sut;
    private FakeBD fakeBD;

    @BeforeEach
    void setup(){
        fakeBD = Mockito.mock(FakeBD.class);
        sut = new CursoService(fakeBD);
    }

    @Test
    void testObtenerTodosCursos_shouldReturnEveryCourse() {
        // given

        // when
        sut.obtenerTodosCursos();

        // then
        verify(fakeBD, times(1)).getListaCursos();
    }

    @Test
    void testobtenerCursoDTOID_findCursoThatExists(){
        // given
        String idPrueba = "C001";
        // when
        CursoDTO curso = sut.obtenerCursoDTOID(idPrueba);
        CursoDTO cursoexpected = new CursoDTO("C001","Introducción a la programación", "Informática",
                40, Modalidad.PRESENCIAL, 99.99);
        // then
        Assertions.assertEquals(curso, cursoexpected);
    }

    @Test
    void testobtenerCursoDTOID_findCursoThatNotExists(){
        // given
        String idPrueba = "ABCD";
        // when
        CursoDTO curso = sut.obtenerCursoDTOID(idPrueba);
        // then
        Assertions.assertNull(curso);
    }

    @Test
    void testMatricular_matricularCorrectly(){
        // given
        List<Formador> formadores1 = new ArrayList<>();
        formadores1.add(new Formador("Miguel", "Castañon", "linkedin.com/miguel"));
        formadores1.add(new Formador("Jose", "Antonio", "linkedin.com/chose"));
        Curso curso = new Curso("C001", "Introducción a la programación", "Informática", 40,
                Modalidad.PRESENCIAL, 99.99, true, "Calle 123", new Date(), new Date(), formadores1);
        Alumno alumno = new Alumno("Borja", "Baston", "12345678A");


        // when
        sut.matricularAlumno(alumno, "C001");

        // then
        Assertions.assertTrue(sut.getMatriculados().containsValue(alumno));
        Assertions.assertTrue(sut.getMatriculados().containsKey(curso));
    }

    @Test
    void testMatricular_matricularCursoNoExiste(){
        // given
        Alumno alumno = new Alumno("Borja", "Baston", "12345678A");

        // when + then
        assertThrows(IllegalArgumentException.class, () -> {
            sut.matricularAlumno(alumno,"ABCD");
        });
    }

    @Test
    void testobtenerCursoModalidad_modalidadOnline(){
        // when
        List<Curso> cursotest = sut.obtenerCursoModalidad("online");

        // then
        assertEquals(1, cursotest.size());
    }

    @Test
    void testobtenerCursoModalidad_modalidadInvalida(){
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            sut.obtenerCursoModalidad("ABCD");
        });
    }




}