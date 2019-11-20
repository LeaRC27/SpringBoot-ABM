package com.example.dao;

import com.example.model.Persona;

import java.util.List;

public interface PersonaDao {

    List<Persona> buscarPersonas();

    Persona buscarPersona(int Id);

    void guardarPersona(Persona p);

    void modificarPersona(Persona p);

    void eliminarPersona(Persona p);

}
