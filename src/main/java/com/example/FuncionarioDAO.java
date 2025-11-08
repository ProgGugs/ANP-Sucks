package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FuncionarioDAO {

    private final String supabaseUrl;
    private final String supabaseKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public FuncionarioDAO(String supabaseUrl, String supabaseKey) {
        this.supabaseUrl = supabaseUrl.endsWith("/") ? supabaseUrl + "Funcionario" : supabaseUrl + "/Funcionario";
        this.supabaseKey = supabaseKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private Map<String, String> headers() {
        Map<String, String> h = new HashMap<>();
        h.put("apikey", supabaseKey);
        h.put("Authorization", "Bearer " + supabaseKey);
        h.put("Content-Type", "application/json");
        h.put("Accept", "application/json");
        return h;
    }

    public List<Funcionario> listarTodos() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<Funcionario>>() {
                });
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Funcionario buscarPorId(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?id=eq." + id))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && !response.body().equals("[]")) {
                List<Funcionario> list = objectMapper.readValue(response.body(),
                        new TypeReference<List<Funcionario>>() {
                        });
                return list.isEmpty() ? null : list.get(0);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean criar(Funcionario f) {
        try {
            String supabaseUrl = System.getenv("SUPABASE_URL");
            String supabaseKey = System.getenv("SUPABASE_KEY");

            if (supabaseUrl == null || supabaseKey == null) {
                System.err.println("Variáveis de ambiente SUPABASE_URL ou SUPABASE_KEY não configuradas.");
                return false;
            }

            // Constrói o corpo JSON da requisição
            String json = new ObjectMapper().writeValueAsString(f);

            // Monta o cliente e a requisição
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "/Funcionario"))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            // Envia e obtém a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Supabase resposta: " + response.statusCode());
            System.out.println("Corpo da resposta: " + response.body());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return true;
            } else {
                System.err.println("Erro do Supabase: " + response.body());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(int id, Funcionario func) {
        try {
            String json = objectMapper.writeValueAsString(func);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?id=eq." + id))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey, "Content-Type",
                            "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 204 || response.statusCode() == 200;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?id=eq." + id))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 204 || response.statusCode() == 200;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
