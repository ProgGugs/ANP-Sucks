package com.example;

public class Solicitante extends Usuario {
    private PedidoRecord pedido;

    public Solicitante(Usuario usuario) {
        super(usuario.getNome(), usuario.getCpf(), 
              usuario.getEmail(), usuario.getEndereco(), usuario.getSenha(), usuario.isFlagReitor());
    }

    public void fazerPedido(Long numero, String data, Double valor, Boolean flagUniversidade, String descricao,
            String objetivo, String status, Long solicitante) {
        if (this.pedido == null) {
            this.pedido = new PedidoRecord(numero, data, valor, flagUniversidade, descricao, objetivo, status, solicitante);
            System.out.println("Pedido realizado com sucesso!");
            return;
        }
        System.out.println("Pedido não pode ser realizado...");
    }

    public String consultarStatus() {
        if (pedido == null) {
            return "Nenhum pedido encontrado.";
        }
        return pedido.getStatus();
    }

    public boolean isContemplado() {
        return pedido != null && "Aprovado".equalsIgnoreCase(pedido.getStatus());
    }

    public PedidoRecord getPedido() {
        return pedido;
    }
}
