package es.babel.buscadorcursos.model;

import es.babel.buscadorcursos.model.enums.Modalidad;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Curso {

    private String cursoID;
    private String nombreCurso;
    private String area;
    private double numeroHoras;
    private Modalidad modalidad;
    private double precio;
    private boolean certificacion;
    private String direccion;
    private List<Formador> formadores;
    private Date fechaInicio;
    private Date fechaFin;
}
