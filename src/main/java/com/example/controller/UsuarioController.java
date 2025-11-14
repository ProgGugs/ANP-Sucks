package com.example.controller;

import com.example.UsuarioDAO;
import com.example.UsuarioRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.util.List;
import java.util.Objects;


public class UsuarioController {

    private final UsuarioDAO dao;
    private final ObjectMapper mapper = new ObjectMapper();

    public UsuarioController(String supabaseUrl, String supabaseKey) {
        this.dao = new UsuarioDAO(supabaseUrl, supabaseKey);
    }

    // GET /Usuario
    public void getAll(Context ctx) {
        List<UsuarioRecord> usuarios = dao.listarTodos();
        ctx.json(usuarios);
    }

    // GET /Usuario/{id}
    public void getById(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        UsuarioRecord u = dao.buscarPorId(id);
        if (u == null) ctx.status(404).result("Usuario não encontrado");
        else ctx.json(u);
    }

    // POST /Usuario
    public void create(Context ctx) {
        try {
            UsuarioRecord body = ctx.bodyAsClass(UsuarioRecord.class);
            var res = dao.criarRaw(body);

            if (res.ok()) {
                // PostgREST retorna array com o registro inserido
                try {
                    if (res.body != null && res.body.trim().startsWith("[")) {
                        var list = mapper.readValue(res.body, new TypeReference<List<UsuarioRecord>>() {});
                        if (!list.isEmpty()) {
                            ctx.status(201).json(list.get(0));
                            return;
                        }
                    }
                } catch (Exception ignored) {}
                ctx.status(201).json(body);
            } else {
                ctx.status(res.status).result(Objects.toString(res.body, "Falha ao inserir Usuario"));
            }
        } catch (Exception e) {
            ctx.status(400).result("Erro no parse do JSON: " + e.getMessage());
        }
    }

    // PUT /Usuario/{id}
    public void update(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        UsuarioRecord body = ctx.bodyAsClass(UsuarioRecord.class);
        boolean ok = dao.atualizar(id, body);
        if (ok) ctx.status(200).json(body);
        else ctx.status(400).result("Não foi possível atualizar o usuario");
    }

    // DELETE /Usuario/{id}
    public void delete(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        boolean ok = dao.deletar(id);
        if (ok) ctx.status(204);
        else ctx.status(404).result("Usuario não encontrado");
    }
}
