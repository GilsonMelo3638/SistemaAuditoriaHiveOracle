package br.gov.df.economia.sistemaauditoriahiveoracle.model.dao;

import br.gov.df.economia.sistemaauditoriahiveoracle.model.entities.Evento;

import java.util.List;

public interface EventoDao {

    Evento findById(String id);

    List<Evento> findAll();
}