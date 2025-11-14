package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** DAO HTTP → Supabase para a tabela "Usuario". */
public class UsuarioDAO {

    private final String supabaseUrl; // .../rest/v1/Usuario
    private final String supabaseKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public UsuarioDAO(String supabaseBaseUrl, String supabaseKey) {
        this.supabaseUrl = supabaseBaseUrl.endsWith("/") ? supabaseBaseUrl + "Usuario" : supabaseBaseUrl + "/Usuario";
        this.supabaseKey = supabaseKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /** Resultado HTTP bruto para debug/propagação ao controller. */
    public static class HttpResult {
        public final int status;
        public final String body;
        public HttpResult(int status, String body) { this.status = status; this.body = body; }
        public boolean ok() { return status >= 200 && status < 300; }
    }

    public List<UsuarioRecord> listarTodos() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET().build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200) {
                return objectMapper.readValue(res.body(), new TypeReference<List<UsuarioRecord>>() {});
            } else {
                // System.err.println("[UsuarioDAO.listarTodos] status=" + res.statusCode() + " body=" + res.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public UsuarioRecord buscarPorId(long id) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?id=eq." + id))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET().build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200 && res.body() != null && !res.body().equals("[]")) {
                List<UsuarioRecord> list = objectMapper.readValue(res.body(), new TypeReference<List<UsuarioRecord>>() {});
                return list.isEmpty() ? null : list.get(0);
            } else {
                // System.err.println("[UsuarioDAO.buscarPorId] status=" + res.statusCode() + " body=" + res.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UsuarioRecord buscarPorEmail(String email) {
        try {
            String emailEncoded = URLEncoder.encode(email, StandardCharsets.UTF_8);
            String url = supabaseUrl + "?email=eq." + emailEncoded;

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200 && res.body() != null && !res.body().equals("[]")) {
                List<UsuarioRecord> usuarios =
                        objectMapper.readValue(res.body(), new TypeReference<List<UsuarioRecord>>() {});

                return usuarios.isEmpty() ? null : usuarios.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public UsuarioRecord buscarPorSenha(String senha) {
        try {
            String senhaEncoded = URLEncoder.encode(senha, StandardCharsets.UTF_8);
            String url = supabaseUrl + "?senha=eq." + senhaEncoded;

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers(
                            "apikey", supabaseKey,
                            "Authorization", "Bearer " + supabaseKey
                    )
                    .GET()
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200 && res.body() != null && !res.body().equals("[]")) {
                List<UsuarioRecord> list = objectMapper.readValue(
                        res.body(),
                        new TypeReference<List<UsuarioRecord>>() {}
                );
                return list.isEmpty() ? null : list.get(0);
            } else {
                // System.err.println("[UsuarioDAO.buscarPorSenha] status=" +
                //         res.statusCode() + " body=" + res.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public HttpResult criarRaw(UsuarioRecord u) {
        try {
            String json = objectMapper.writeValueAsString(u);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey,
                             "Content-Type", "application/json", "Prefer", "return=representation")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            // System.out.println("[UsuarioDAO.criar] status=" + res.statusCode() + " body=" + res.body());
            return new HttpResult(res.statusCode(), res.body());

        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(500, "Erro cliente HTTP: " + e.getMessage());
        }
    }

    public boolean criar(UsuarioRecord u) {
        return criarRaw(u).ok();
    }

    public boolean atualizar(long id, UsuarioRecord u) {
        try {
            String json = objectMapper.writeValueAsString(u);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?id=eq." + id))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey,
                             "Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() != 200 && res.statusCode() != 204) {
                // System.err.println("[UsuarioDAO.atualizar] status=" + res.statusCode() + " body=" + res.body());
            }
            return res.statusCode() == 200 || res.statusCode() == 204;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(long id) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?id=eq." + id))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .DELETE().build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() != 200 && res.statusCode() != 204) {
                System.err.println("[UsuarioDAO.deletar] status=" + res.statusCode() + " body=" + res.body());
            }
            return res.statusCode() == 200 || res.statusCode() == 204;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
