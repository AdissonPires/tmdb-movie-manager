package dao;

import model.Filmes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    private Connection conexao;

    public FilmeDAO() {
        ConexaoFactory factory = new ConexaoFactory();
        this.conexao = factory.recuperaConexao();
        this.criaTabela(); // Garante que a tabela exista
    }

    // Método auxiliar para criar a tabela automaticamente
    private void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS filmes (" +
                     "id INTEGER PRIMARY KEY, " +
                     "titulo TEXT, " +
                     "sinopse TEXT, " +
                     "lancamento TEXT, " +
                     "nota REAL)";
        
        try (Statement stmt = this.conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // 1. CREATE (Salvar um filme no banco)
    public void salvar(Filmes filme) {
        String sql = "INSERT OR IGNORE INTO filmes (id, titulo, sinopse, lancamento, nota) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, filme.getId());
            stmt.setString(2, filme.getTitle());
            stmt.setString(3, filme.getSinopse());
            stmt.setString(4, filme.getDataLancamento());
            stmt.setDouble(5, filme.getNota());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar filme: " + e.getMessage());
        }
    }

    // 2. READ (Listar todos os filmes salvos)
    public List<Filmes> listar() {
        List<Filmes> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Filmes f = new Filmes();
                f.setId(rs.getInt("id"));
                f.setTitle(rs.getString("titulo"));
                f.setSinopse(rs.getString("sinopse"));
                f.setDataLancamento(rs.getString("lancamento"));
                f.setNota(rs.getDouble("nota"));
                
                filmes.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar filmes: " + e.getMessage());
        }
        return filmes;
    }

    // 3. UPDATE (Atualizar a nota ou sinopse de um filme)
    public void atualizar(Filmes filme) {
        String sql = "UPDATE filmes SET titulo = ?, sinopse = ?, nota = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, filme.getTitle());
            stmt.setString(2, filme.getSinopse());
            stmt.setDouble(3, filme.getNota());
            stmt.setInt(4, filme.getId()); // O ID é usado para achar o filme certo

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    // 4. DELETE (Apagar um filme pelo ID)
    public void deletar(int id) {
        String sql = "DELETE FROM filmes WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar filme: " + e.getMessage());
        }
    }
}