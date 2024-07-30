package br.gov.df.economia.sistemaauditoriahiveoracle.model.dao;

import java.util.List;

import br.gov.df.economia.sistemaauditoriahiveoracle.model.entities.Evento;
import br.gov.df.economia.sistemaauditoriahiveoracle.model.entities.NFe;

public interface NfeDao {

	NFe findByChave(String id);
	List<NFe> findAll();
	List<NFe> findByEvento(Evento evento);
}