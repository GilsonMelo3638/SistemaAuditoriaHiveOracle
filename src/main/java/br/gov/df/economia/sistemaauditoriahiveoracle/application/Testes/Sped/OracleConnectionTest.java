package br.gov.df.economia.sistemaauditoriahiveoracle.application.Testes.Sped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionTest {

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Oracle JDBC registrado com sucesso!");

            String url = "jdbc:oracle:thin:@//10.70.202.68:1521/oraprd12";
            String username = System.getenv("SPED_JDBC_USER");
            String password = System.getenv("SPED_JDBC_PASSWORD");

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

            connection.close();

        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao registrar o driver Oracle JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}

