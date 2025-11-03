import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO {

    private final List<Funcionario> pedidos = new ArrayList<>();
    private int nextId = 1;

    public List<Funcionario> listarTodos() {
        return pedidos;
    }

    public Funcionario buscarPorId(int id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void criar(Funcionario pedido) {
        pedido.setId(nextId++);
        pedidos.add(pedido);
    }

    public boolean atualizar(int id, Funcionario dadosAtualizados) {
        Optional<Funcionario> pedidoOpt = pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (pedidoOpt.isPresent()) {
            Funcionario existente = pedidoOpt.get();
            existente.setNome(dadosAtualizados.getNome());
            existente.setCargo(dadosAtualizados.getCargo());
            existente.setMatricula(dadosAtualizados.getMatricula());
            return true;
        }
        return false;
    }

    public boolean deletar(int id) {
        return pedidos.removeIf(p -> p.getId() == id);
    }
}
