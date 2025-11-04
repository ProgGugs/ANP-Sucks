package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO {

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private int nextId = 1;

    public List<Funcionario> listarTodos() {
        return funcionarios;
    }

    public Funcionario buscarPorId(int id) {
        return funcionarios.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void criar(Funcionario func) {
        func.setId(nextId++);
        funcionarios.add(func);
    }

    public boolean atualizar(int id, Funcionario dadosAtualizados) {
        Optional<Funcionario> funcionarioOpt = funcionarios.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (funcionarioOpt.isPresent()) {
            Funcionario existente = funcionarioOpt.get();
            existente.setNome(dadosAtualizados.getNome());
            existente.setCargo(dadosAtualizados.getCargo());
            existente.setMatricula(dadosAtualizados.getMatricula());
            return true;
        }
        return false;
    }

    public boolean deletar(int id) {
        return funcionarios.removeIf(p -> p.getId() == id);
    }
}
