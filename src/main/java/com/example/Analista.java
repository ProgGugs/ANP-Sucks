package com.example;

public class Analista extends Funcionario {

    public Analista(int id, String cpf, String matricula, double salario, String cargo, String nome, String email,
            String senha, String endereco) {
        super(id, cpf, matricula, salario, cargo, nome, email, senha, endereco);
    }

    public boolean consultarPedidos() {
        return true;
    }

    public boolean redirecionarPedidos() {
        return true;
    }

    public boolean consultarContemplados() {
        return true;
    }
}
