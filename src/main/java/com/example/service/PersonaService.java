package com.example.service;

import com.example.model.Persona;

import java.util.List;

public interface PersonaService {

    List<Persona> buscarPersonas();

    Persona buscarPersona(int Id);

    void guardarPersona(Persona p);

    void modificarPersona(Persona p);

    void eliminarPersona(Persona p);
}
