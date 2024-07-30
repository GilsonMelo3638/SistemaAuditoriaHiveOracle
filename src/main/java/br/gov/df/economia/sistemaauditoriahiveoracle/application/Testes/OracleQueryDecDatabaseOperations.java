package br.gov.df.economia.sistemaauditoriahiveoracle.application.Testes;


import br.gov.df.economia.sistemaauditoriahiveoracle.db.OracleDecDatabaseOperations;

public class OracleQueryDecDatabaseOperations {

    public static void main(String[] args) {
        OracleDecDatabaseOperations dbOperations = new OracleDecDatabaseOperations();
        dbOperations.executeQueryAndSaveToCSV();
    }
}