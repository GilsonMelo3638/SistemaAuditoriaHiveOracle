module com.example.dechivejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires java.sql;
    requires jsch;

    opens br.gov.df.economia.sistemaauditoriahiveoracle to javafx.fxml;
    opens br.gov.df.economia.sistemaauditoriahiveoracle.gui to javafx.fxml;
    opens br.gov.df.economia.sistemaauditoriahiveoracle.model.entities to javafx.base;

    exports br.gov.df.economia.sistemaauditoriahiveoracle.application;
    exports br.gov.df.economia.sistemaauditoriahiveoracle.gui;
    exports br.gov.df.economia.sistemaauditoriahiveoracle.Validacoes;
    exports br.gov.df.economia.sistemaauditoriahiveoracle.application.Testes;
    exports br.gov.df.economia.sistemaauditoriahiveoracle.application.Testes.Sped;
}

