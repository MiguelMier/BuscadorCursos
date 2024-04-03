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
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CursoServiceTest {

    private CursoService sut;
    private FakeBD fakeBD;
    @Captor
    private ArgumentCaptor<Curso> cursoCaptor;
    @TempDir
    Path tempDir;

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
        assertEquals(2, sut.obtenerTodosCursos().size());
        //verify(fakeBD, times(1)).getListaCursos();
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
        Assertions.assertEquals(curso, sut.getMatriculados().keySet().stream().toList().get(0));
    }

    @Test
    void testMatricular_shouldThrowIllegalArgument_matricularCursoNoExiste(){
        // given
        Alumno alumno = new Alumno("Borja", "Baston", "12345678A");

        // when + then
        assertThrows(IllegalArgumentException.class, () -> {
            sut.matricularAlumno(alumno,"ABCD");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sut.matricularAlumno(alumno,"");
        });
    }

    @Test
    void testMatricular_matricularAlumnoNoExiste(){
        assertThrows(IllegalArgumentException.class, () -> {
            sut.matricularAlumno(null,"C001");
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

    @Test
    void testNombreArchivoConFecha_shouldHaveNameCorrect() {
        String nombreFichero = "estadisticas.txt";

        sut.escribirEstadisticasEnArchivo(nombreFichero);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String nombreArchivoConFecha = sdf.format(new Date()) + " - " + nombreFichero;

        File archivo = new File(nombreArchivoConFecha);
        assertTrue(archivo.exists());
        // comprobar fichero guardado correctamente con el nombre --> cumplir las propiedades de un test unitario
        assertTrue(verificarContenidoArchivo(nombreArchivoConFecha));
    }

    private boolean verificarContenidoArchivo(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void testEscribirEstadisticasEnArchivo() throws IOException {
        String nombreFichero = "estadisticas.txt";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String nombreArchivoConFecha = sdf.format(new Date()) + " - " + nombreFichero;

        Path archivoTemporal = tempDir.resolve(nombreArchivoConFecha);

        // Llamar al método que escribe las estadísticas en el archivo
        sut.escribirEstadisticasEnArchivo(archivoTemporal.toString());

        // Verificar que se haya escrito algo en el archivo
        //assertTrue(verificarContenidoArchivo(archivoTemporal.toString()));
    }
    @ParameterizedTest
    @CsvSource(value = {
            "qwefwef;0",
            "mich; 5"
    }, delimiter = ';')
    void testParametrizado(String password, int expected){
        assertNotEquals(password.length(), expected);
    }

}