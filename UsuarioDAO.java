import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
 * mudar os comandos sql
 * de acordo com a construção do banco
 * (estava sem acesso ao banco)
 */
public class UsuarioDAO implements IUsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Usuario usuario) {
        String sql = "INSERT INTO Usuario (cpfCnpj, nome, email, endereco, senha, flagReitor) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, usuario.getCpf());
            pst.setString(2, usuario.getNome());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getEndereco());
            pst.setString(5, usuario.getSenha());
            pst.setBoolean(6, usuario.isFlagReitor());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Usuario usuario) {
        String sql = "UPDATE Usuario SET cpfCnpj=?, nome=?, email=?, endereco=?, senha=?, flagReitor=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, usuario.getCpf());
            pst.setString(2, usuario.getNome());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getEndereco());
            pst.setString(5, usuario.getSenha());
            pst.setBoolean(6, usuario.isFlagReitor());
            pst.setInt(7, usuario.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Usuario WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario select(int id) {
        String sql = "SELECT * FROM Usuario WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpfCnpj"),
                        rs.getString("email"),
                        rs.getString("endereco"),
                        rs.getString("senha"),
                        rs.getBoolean("flagReitor")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null; 
    }

    @Override
public List<Usuario> selectAll() {
    String sql = "SELECT * FROM Usuario";
    List<Usuario> usuarios = new ArrayList<>();

    try (PreparedStatement pst = connection.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            usuarios.add(new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpfCnpj"),
                rs.getString("email"),
                rs.getString("endereco"),
                rs.getString("senha"),
                rs.getBoolean("flagReitor")
            ));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao listar usuários: " + e.getMessage());
    }

    return usuarios;
}

}
