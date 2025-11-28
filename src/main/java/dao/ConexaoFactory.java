package dao; // Ajuste se seu pacote for diferente

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    // O método static permite chamar sem precisar dar 'new ConexaoFactory()'
    // mas no seu DAO fizemos instanciando, então vamos manter simples.
    public Connection recuperaConexao() {
        try {
            // 1. A String Mágica de Conexão (JDBC URL)
            // "jdbc:sqlite:banco.db" diz 3 coisas:
            // - jdbc: vamos usar Java Database Connectivity
            // - sqlite: o banco é do tipo SQLite
            // - banco.db: o nome do arquivo que será criado na raiz do projeto
            return DriverManager.getConnection("jdbc:sqlite:banco.db");

        } catch (SQLException e) {
            // Se der erro (ex: disco cheio, sem permissão), avisamos aqui
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}