package com.example.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name="Persona.findAll", query="from Persona"),
        @NamedQuery(name="Persona.findId", query = "select p from Persona p where p.id =: id"),
})
public class Persona {

    private static final Logger logger = LogManager.getLogger(Persona.class);

    @Id
    @GeneratedValue
    private int id;

    private String nombre;

    private String apellido;

    private String telefono;

    @Column(unique = true)
    private Long dni;

    public Persona() {
    }

    public Persona(String nombre, String apellido, String telefono, Long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return id == persona.id &&
                dni == persona.dni &&
                nombre.equals(persona.nombre) &&
                apellido.equals(persona.apellido) &&
                telefono.equals(persona.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, telefono, dni);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }

}
