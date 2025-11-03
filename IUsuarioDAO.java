import java.util.List;

public interface IUsuarioDAO {
    boolean create(Usuario usuario);
    boolean update(Usuario usuario);
    boolean delete(int id);
    Usuario select(int id);
    List<Usuario> selectAll();
}
