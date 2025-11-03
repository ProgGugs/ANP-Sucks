public class Diretor extends Funcionario {

    public Diretor(int id, String cpf, String matricula, double salario, String cargo, String nome, String email,
            String senha, String endereco) {
        super(id, cpf, matricula, salario, cargo, nome, email, senha, endereco);
    }

    public boolean aprovarPedido(Pedido pedido) {
        return true;
    }

    public boolean exportarRelatariosAcesso() {
        return true;
    }

    public boolean consultarAcessos() {
        return true;
    }
}
