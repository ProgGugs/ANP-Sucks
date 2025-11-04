package com.example.controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;

import com.example.Funcionario;
import com.example.FuncionarioDAO;

import java.util.List;

public class FuncionarioController {

    private static final FuncionarioDAO funcionarioservice = new FuncionarioDAO();

    public static void registerRoutes(Javalin app) {
        app.get("/funcionario", listarTodos);
        app.get("/funcionario/:id", buscarPorId);
        app.post("/funcionario", criar);
        app.put("/funcionario/:id", atualizar);
        app.delete("/funcionario/:id", deletar);
    }

    // GET /api/funcionarios
    public static Handler getAll = ctx -> {
        List<Funcionario> funcionarios = funcionarioservice.listarTodos();
        ctx.json(funcionarios);
    };

    // GET /api/funcionarios/:id
    public static Handler getById = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Funcionario funcionario = funcionarioservice.buscarPorId(id);
        if (funcionario != null) {
            ctx.json(funcionario);
        } else {
            ctx.status(404).result("Funcionario não encontrado");
        }
    };

    // POST /api/funcionarios
    public static Handler create = ctx -> {
        Funcionario novofuncionario = ctx.bodyAsClass(funcionario.class);
        funcionarioservice.criar(novofuncionario);
        ctx.status(201).json(novofuncionario);
    };

    // PUT /api/funcionarios/:id
    public static Handler update = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Funcionario dadosAtualizados = ctx.bodyAsClass(funcionario.class);
        boolean atualizado = funcionarioservice.atualizar(id, dadosAtualizados);

        if (atualizado) {
            ctx.json(dadosAtualizados);
        } else {
            ctx.status(404).result("Funcionario não encontrado");
        }
    };

    // DELETE /api/funcionarios/:id
    public static Handler delete = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deletado = funcionarioservice.deletar(id);

        if (deletado) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Funcionario não encontrado");
        }
    };
}
