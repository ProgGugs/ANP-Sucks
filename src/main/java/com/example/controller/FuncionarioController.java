package com.example.controller;

import io.javalin.http.Context;
import com.example.Funcionario;
import com.example.FuncionarioDAO;

import java.util.List;

public class FuncionarioController {

    private final FuncionarioDAO dao;

    // O DAO precisa das credenciais do Supabase (passadas pela Main)
    public FuncionarioController(String supabaseUrl, String supabaseKey) {
        this.dao = new FuncionarioDAO(supabaseUrl, supabaseKey);
    }

    // GET /funcionario
    public void getAll(Context ctx) {
        List<Funcionario> funcionarios = dao.listarTodos();
        ctx.json(funcionarios);
    }

    // GET /funcionario/{id}
    public void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Funcionario funcionario = dao.buscarPorId(id);
        if (funcionario != null) {
            ctx.json(funcionario);
        } else {
            ctx.status(404).result("Funcionário não encontrado");
        }
    }

    // POST /funcionario
    public void create(Context ctx) {
        Funcionario novo = ctx.bodyAsClass(Funcionario.class);
        boolean criado = dao.criar(novo);
        if (criado) {
            ctx.status(201).json(novo);
        } else {
            ctx.status(500).result("Erro ao criar funcionário no Supabase");
        }
    }

    // PUT /funcionario/{id}
    public void update(Context ctx) {
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
    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deletado = dao.deletar(id);
        if (deletado) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Funcionário não encontrado");
        }
    }
}
