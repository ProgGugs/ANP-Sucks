package com.example;

import io.javalin.Javalin;
import com.example.controller.FuncionarioController;

public class Main {

    public static void main(String[] args) {

        // ⚙️ Ler variáveis de ambiente do Supabase
        String supabaseUrl = System.getenv("SUPABASE_URL");
        String supabaseKey = System.getenv("SUPABASE_KEY");

        if (supabaseUrl == null || supabaseKey == null) {
            System.err.println("❌ Erro: SUPABASE_URL e SUPABASE_KEY não foram definidas como variáveis de ambiente.");
            System.exit(1);
        }

        // 🔒 Cria o servidor Javalin configurado para HTTPS (se certificado disponível)
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.showJavalinBanner = false;

        }).start(7000); // ✅ Porta local (https://localhost:7000)

        // Instancia o controller, passando as credenciais do Supabase
        FuncionarioController funcionarioController = new FuncionarioController(supabaseUrl, supabaseKey);

        // 🌐 Define as rotas HTTPS (REST API)
        app.get("/Funcionario", funcionarioController::getAll);         // GET - lista todos
        app.get("/Funcionario/{id}", funcionarioController::getById);   // GET - por ID
        app.post("/Funcionario", funcionarioController::create);        // POST - cria
        app.put("/Funcionario/{id}", funcionarioController::update);    // PUT - atualiza
        app.delete("/Funcionario/{id}", funcionarioController::delete); // DELETE - remove

        System.out.println("Servidor HTTPS rodando em: https://localhost:7000");
    }
}
