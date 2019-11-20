package com.example.dao;

import com.example.model.Persona;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PersonaDaoJPA implements PersonaDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Persona> buscarPersonas() {
        //noinspection JpaQueryApiInspection
        return (List<Persona>) entityManager.createNamedQuery("Persona.findAll").getResultList();
    }

    @Override
    public Persona buscarPersona(int Id){
        //noinspection JpaQueryApiInspection
        return (Persona) entityManager.createNamedQuery("Persona.findId").setParameter("id",Id).getSingleResult();
    }

    @Override
    public void guardarPersona(Persona p) {
        entityManager.persist(p);
    }

    @Override
    public void modificarPersona (Persona p){
        entityManager.merge(p);
    }

    @Override
    public void eliminarPersona(Persona p) {
        Persona persona = buscarPersona(p.getId());
        entityManager.remove(persona);
    }

}
