package es.babel.buscadorcursos.model;

import es.babel.buscadorcursos.model.enums.Modalidad;
import lombok.Data;

import java.util.ArrayList;
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

    public Curso(String cursoID, String nombreCurso, String area, double numeroHoras, Modalidad modalidad,
                 double precio, boolean certificacion, String direccion, Date fechaInicio, Date fechaFin, List<Formador> formadores) {
        this.cursoID = cursoID;
        this.nombreCurso = nombreCurso;
        this.area = area;
        this.numeroHoras = numeroHoras;
        this.modalidad = modalidad;
        this.precio = precio;
        this.certificacion = certificacion;
        this.direccion = direccion;
        this.formadores = formadores;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
