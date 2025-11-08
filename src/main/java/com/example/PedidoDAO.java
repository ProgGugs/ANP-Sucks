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

/**
 * DAO que conversa com o Supabase REST (PostgREST).
 * SUPABASE_URL deve ser algo como: https://<ref>.supabase.co/rest/v1
 * Este DAO concatena "/Pedido" automaticamente.
 */
public class PedidoDAO {

    private final String supabaseUrl; // .../rest/v1/Pedido
    private final String supabaseKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PedidoDAO(String supabaseBaseUrl, String supabaseKey) {
        this.supabaseUrl = supabaseBaseUrl.endsWith("/") ? supabaseBaseUrl + "Pedido" : supabaseBaseUrl + "/Pedido";
        this.supabaseKey = supabaseKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /** Helper p/ debug do HTTP */
    public static class HttpResult {
        public final int status;
        public final String body;
        public HttpResult(int status, String body) { this.status = status; this.body = body; }
        public boolean ok() { return status >= 200 && status < 300; }
        @Override public String toString() { return "HttpResult{status=" + status + ", body=" + body + '}'; }
    }

    public List<PedidoRecord> listarTodos() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<PedidoRecord>>() {});
            } else {
                System.err.println("[PedidoDAO.listarTodos] status=" + response.statusCode() + " body=" + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public PedidoRecord buscarPorNumero(long numero) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?numero=eq." + numero))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 && response.body() != null && !response.body().equals("[]")) {
                List<PedidoRecord> list = objectMapper.readValue(response.body(), new TypeReference<List<PedidoRecord>>() {});
                return list.isEmpty() ? null : list.get(0);
            } else {
                System.err.println("[PedidoDAO.buscarPorNumero] status=" + response.statusCode() + " body=" + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Versão que retorna status+body do Supabase (para o controller repassar o erro real). */
    public HttpResult criarRaw(PedidoRecord pedido) {
        try {
            String json = objectMapper.writeValueAsString(pedido);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey,
                             "Content-Type", "application/json",
                             "Prefer", "return=representation")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[PedidoDAO.criar] status=" + response.statusCode() + " body=" + response.body());
            return new HttpResult(response.statusCode(), response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(500, "Erro cliente HTTP: " + e.getMessage());
        }
    }

    /** Compatibilidade com usos que só querem boolean. */
    public boolean criar(PedidoRecord pedido) {
        return criarRaw(pedido).ok();
    }

    public boolean atualizar(long numero, PedidoRecord pedido) {
        try {
            String json = objectMapper.writeValueAsString(pedido);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?numero=eq." + numero))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey,
                             "Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200 && response.statusCode() != 204) {
                System.err.println("[PedidoDAO.atualizar] status=" + response.statusCode() + " body=" + response.body());
            }
            return response.statusCode() == 200 || response.statusCode() == 204;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(long numero) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "?numero=eq." + numero))
                    .headers("apikey", supabaseKey, "Authorization", "Bearer " + supabaseKey)
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200 && response.statusCode() != 204) {
                System.err.println("[PedidoDAO.deletar] status=" + response.statusCode() + " body=" + response.body());
            }
            return response.statusCode() == 200 || response.statusCode() == 204;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
