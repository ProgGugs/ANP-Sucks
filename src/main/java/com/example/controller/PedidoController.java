package com.example.controller;

import io.javalin.http.Context;
import com.example.PedidoDAO;
import com.example.PedidoRecord;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PedidoController {

    private final PedidoDAO dao;
    private final ObjectMapper mapper = new ObjectMapper();

    public PedidoController(String supabaseUrl, String supabaseKey) {
        this.dao = new PedidoDAO(supabaseUrl, supabaseKey);
    }

    public void getAll(Context ctx) {
        List<PedidoRecord> pedidos = dao.listarTodos();
        ctx.json(pedidos);
    }

    public void getByNumero(Context ctx) {
        long numero = Long.parseLong(ctx.pathParam("numero"));
        PedidoRecord pedido = dao.buscarPorNumero(numero);
        if (pedido == null) {
            ctx.status(404).result("Pedido não encontrado");
        } else {
            ctx.json(pedido);
        }
    }

    public void create(Context ctx) {
        try {
            PedidoRecord body = ctx.bodyAsClass(PedidoRecord.class);
            var res = dao.criarRaw(body);

            if (res.ok()) {
                // Supabase com Prefer:return=representation costuma devolver um array com o registro inserido
                try {
                    if (res.body != null && res.body.trim().startsWith("[")) {
                        var list = mapper.readValue(res.body, new TypeReference<List<PedidoRecord>>() {});
                        if (!list.isEmpty()) {
                            ctx.status(201).json(list.get(0));
                            return;
                        }
                    }
                } catch (Exception ignored) {}
                ctx.status(201).json(body);
            } else {
                ctx.status(res.status).result(Objects.toString(res.body, "Falha ao inserir no Supabase"));
            }
        } catch (Exception e) {
            ctx.status(400).result("Erro no parse do JSON: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        long numero = Long.parseLong(ctx.pathParam("numero"));
        PedidoRecord body = ctx.bodyAsClass(PedidoRecord.class);
        boolean ok = dao.atualizar(numero, body);
        if (ok) {
            ctx.status(200).json(body);
        } else {
            ctx.status(400).result("Não foi possível atualizar o pedido");
        }
    }

    public void delete(Context ctx) {
        long numero = Long.parseLong(ctx.pathParam("numero"));
        boolean ok = dao.deletar(numero);
        if (ok) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Pedido não encontrado");
        }
    }
}
