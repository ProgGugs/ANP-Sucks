package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, cargo, matricula FROM funcionarios";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("matricula"),
                        rs.getString("cargo"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"));
                funcionarios.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT id, nome, cargo, matricula, email, senha FROM funcionarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("matricula"),
                        rs.getString("cargo"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void criar(Funcionario func) {
        String sql = "INSERT INTO funcionarios (nome, cargo, matricula, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, func.getNome());
            ps.setString(2, func.getCargo());
            ps.setString(3, func.getMatricula());
            ps.setString(4, func.getEmail());
            ps.setString(5, func.getSenha());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean atualizar(int id, Funcionario func) {
        String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, matricula = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, func.getNome());
            ps.setString(2, func.getCargo());
            ps.setString(3, func.getMatricula());
            ps.setInt(4, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
