package com.example;

import io.javalin.Javalin;

import com.example.controller.FuncionarioController;
import com.example.controller.PedidoController;
import com.example.controller.UsuarioController;

public class Main {

    public static void main(String[] args) {

        // Lê as credenciais do Supabase do ambiente, igual Funcionario já usa
        String supabaseUrl = System.getenv("SUPABASE_URL"); // ex: https://<ref>.supabase.co/rest/v1
        String supabaseKey = System.getenv("SUPABASE_KEY"); // anon key

        if (supabaseUrl == null || supabaseKey == null) {
            System.err.println("Defina SUPABASE_URL e SUPABASE_KEY nas variáveis de ambiente.");
            System.exit(1);
        }

        // Cria app Javalin (ajuste para HTTPS se o seu projeto já usa)
        Javalin app = Javalin.create(cfg -> {
            cfg.showJavalinBanner = false;
            cfg.http.defaultContentType = "application/json";
        }).start(7000);

        // Controllers existentes
        FuncionarioController funcionarioController = new FuncionarioController(supabaseUrl, supabaseKey);

        PedidoController pedidoController = new PedidoController(supabaseUrl, supabaseKey);

        UsuarioController usuarioController = new UsuarioController(supabaseUrl, supabaseKey);

        // ROTAS
        app.get("/Funcionario", funcionarioController::getAll);
        app.get("/Funcionario/{id}", funcionarioController::getById);
        app.post("/Funcionario", funcionarioController::create);
        app.put("/Funcionario/{id}", funcionarioController::update);
        app.delete("/Funcionario/{id}", funcionarioController::delete);

        app.get("/Pedido", pedidoController::getAll);
        app.get("/Pedido/{numero}", pedidoController::getByNumero);
        app.post("/Pedido", pedidoController::create);
        app.put("/Pedido/{numero}", pedidoController::update);
        app.delete("/Pedido/{numero}", pedidoController::delete);

        app.get("/Usuario", usuarioController::getAll);
        app.get("/Usuario/{id}", usuarioController::getById);
        app.post("/Usuario", usuarioController::create);
        app.put("/Usuario/{id}", usuarioController::update);
        app.delete("/Usuario/{id}", usuarioController::delete);

        System.out.println("Servidor rodando em http://localhost:7000");
        System.out.println();

        Menu menu = new Menu();
        menu.loop();
        System.exit(0);
    }
}
