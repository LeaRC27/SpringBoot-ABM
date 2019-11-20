package com.example.service;

import com.example.dao.PersonaDao;
import com.example.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService{

    @Autowired
    PersonaDao personaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> buscarPersonas() {
        return personaDao.buscarPersonas();
    }

    @Override
    @Transactional(readOnly = true)
    public Persona buscarPersona(int Id){
        return personaDao.buscarPersona(Id);
    }

    @Override
    @Transactional
    public void guardarPersona(Persona p) {
        personaDao.guardarPersona(p);
    }

    @Override
    @Transactional
    public void modificarPersona(Persona p){
        personaDao.modificarPersona(p);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, readOnly = false )
    public void eliminarPersona(Persona p) {
        personaDao.eliminarPersona(p);
    }

}
