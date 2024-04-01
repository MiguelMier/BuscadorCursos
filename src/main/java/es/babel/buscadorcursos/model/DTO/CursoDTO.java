package es.babel.buscadorcursos.model.DTO;

import es.babel.buscadorcursos.model.enums.Modalidad;
import lombok.Data;

@Data
public class CursoDTO {

    private String idCurso;
    private String nombre;
    private String area;
    private double numeroHora;
    private Modalidad modalidad;
    private double precio;
}
