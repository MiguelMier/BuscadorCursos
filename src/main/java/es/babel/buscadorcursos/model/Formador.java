package es.babel.buscadorcursos.model;

import lombok.Data;

@Data
public class Formador {

    private String nombre;
    private String apellido;
    private String linkedin;

    public Formador(String nombre, String apellido, String linkedin) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.linkedin = linkedin;
    }
}
