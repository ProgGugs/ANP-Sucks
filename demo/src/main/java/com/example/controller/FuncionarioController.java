package com.example.controller;

import io.javalin.http.Context;
import com.example.Funcionario;
import com.example.FuncionarioDAO;
import java.util.List;

public class FuncionarioController {

    private static final FuncionarioDAO dao = new FuncionarioDAO();

    // GET /funcionario
    public static void getAll(Context ctx) {
        List<Funcionario> funcionarios = dao.listarTodos();
        ctx.json(funcionarios);
    }

    // GET /funcionario/{id}
    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Funcionario funcionario = dao.buscarPorId(id);
        if (funcionario != null) {
            ctx.json(funcionario);
        } else {
            ctx.status(404).result("Funcionário não encontrado");
        }
    }

    // POST /funcionario
    public static void create(Context ctx) {
        Funcionario novo = ctx.bodyAsClass(Funcionario.class);
        dao.criar(novo);
        ctx.status(201).json(novo);
    }

    // PUT /funcionario/{id}
    public static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Funcionario dadosAtualizados = ctx.bodyAsClass(Funcionario.class);
        boolean atualizado = dao.atualizar(id, dadosAtualizados);
        if (atualizado) {
            ctx.status(200).result("Funcionário atualizado com sucesso");
        } else {
            ctx.status(404).result("Funcionário não encontrado");
        }
    }

    // DELETE /funcionario/{id}
    public static void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deletado = dao.deletar(id);
        if (deletado) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Funcionário não encontrado");
        }
    }
}
