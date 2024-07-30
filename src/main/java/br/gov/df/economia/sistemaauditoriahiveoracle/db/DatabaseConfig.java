package br.gov.df.economia.sistemaauditoriahiveoracle.db;

import br.gov.df.economia.sistemaauditoriahiveoracle.gui.util.Configuracao;
import br.gov.df.economia.sistemaauditoriahiveoracle.Validacoes.ConnectionStatus;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConfig {

    private static final String CONFIG_FILE = "db.properties";
    private static Properties properties = new Properties();
    private static Connection conn;

    static {
        loadConfigurations();
    }

    public static void setConnectionStatus(ConnectionStatus status) {
        Configuracao.connectionOraprd21Status = status;
    }

    public static void loadConfigurations() {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Desculpe, o arquivo " + CONFIG_FILE + " não foi encontrado.");
                return;
            }
            properties.load(input);

            // Substitua as propriedades com base nas variáveis de ambiente
            properties.setProperty("user", System.getenv("SPRING_DATASOURCE_USERNAME"));
            properties.setProperty("password", System.getenv("SPRING_DATASOURCE_PASSWORD"));
            properties.setProperty("dburl", System.getenv("SPRING_DATASOURCE_URL"));
            properties.setProperty("hive.user", System.getenv("HIVE_USERNAME"));
            properties.setProperty("hive.password", System.getenv("HIVE_PASSWORD"));
            properties.setProperty("hive.dburl", System.getenv("HIVE_JDBC_URL"));
            properties.setProperty("hive.zookeeper.namespace", "hiveserver2");

            System.out.println("Configurações carregadas com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar configurações do arquivo " + CONFIG_FILE);
            throw new DatabaseExceptions.DbException(e.getMessage());
        }
    }

    public static String getJdbcUrl() {
        return properties.getProperty("dburl");
    }

    public static String getUsername() {
        return properties.getProperty("user");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static boolean getUseSSL() {
        String useSSL = properties.getProperty("useSSL");
        return useSSL != null && useSSL.equalsIgnoreCase("true");
    }

    public static Connection getConnection() {
        if (Configuracao.connectionOraprd21Status == ConnectionStatus.DISABLED) {
            System.out.println("Connection is disabled.");
            return null;
        }

        if (conn == null) {
            try {
                loadConfigurations();
                System.out.println("Connecting to: " + getJdbcUrl());
                conn = DriverManager.getConnection(getJdbcUrl(), properties);
                System.out.println("Conectado com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                e.printStackTrace();
                throw new DatabaseExceptions.DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DatabaseExceptions.DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DatabaseExceptions.DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DatabaseExceptions.DbException(e.getMessage());
            }
        }
    }

    // Métodos para obter configurações específicas do Hive
    public static String getHiveJdbcUrl() {
        return properties.getProperty("hive.dburl") + ";serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";
    }

    public static String getHiveUsername() {
        return properties.getProperty("hive.user");
    }

    public static String getHivePassword() {
        return properties.getProperty("hive.password");
    }

    public static boolean getHiveUseSSL() {
        String useSSL = properties.getProperty("hive.useSSL");
        return useSSL != null && useSSL.equalsIgnoreCase("true");
    }
}
