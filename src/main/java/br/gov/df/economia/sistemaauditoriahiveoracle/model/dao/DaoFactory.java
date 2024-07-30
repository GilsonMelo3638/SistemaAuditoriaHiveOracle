package br.gov.df.economia.sistemaauditoriahiveoracle.model.dao;

import br.gov.df.economia.sistemaauditoriahiveoracle.db.DatabaseConfig;
import br.gov.df.economia.sistemaauditoriahiveoracle.model.dao.impl.AgendaDaoJDBC;

public class DaoFactory {

	public static AgendaDao createAgendaDao() {
		return new AgendaDaoJDBC(DatabaseConfig.getConnection());
	}
}
