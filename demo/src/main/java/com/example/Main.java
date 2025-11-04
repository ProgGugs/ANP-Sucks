package com.example;

import io.javalin.Javalin;
import com.example.controller.FuncionarioController;

public class Main {
    public static void main(String[] args) {
        // Cria a instância do servidor Javalin
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.showJavalinBanner = false;
        }).start(7000); // porta 7000 (http://localhost:7000)

        // Cria uma instância do controller
        FuncionarioController funcionarioController = new FuncionarioController();

        // Registra as rotas da API REST
        app.get("/funcionario", FuncionarioController::getAll);       // GET
        app.get("/funcionario/{id}", FuncionarioController::getById); // GET por ID
        app.post("/funcionario", FuncionarioController::create);       // POST
        app.put("/funcionario/{id}", FuncionarioController::update);  // PUT
        app.delete("/funcionario/{id}", FuncionarioController::delete);// DELETE

        System.out.println("🚀 Servidor rodando em: http://localhost:7000");
    }
}
