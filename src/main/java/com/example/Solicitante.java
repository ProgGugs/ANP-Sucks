package com.example;

public class Solicitante extends Usuario {
    private Pedido pedido;

    public Solicitante(Usuario usuario) {
        super(usuario.getId(), usuario.getNome(), usuario.getCpf(), 
              usuario.getEmail(), usuario.getEndereco(), usuario.getSenha(), usuario.isFlagReitor());
    }

    public void fazerPedido(String descricao, String universidade) {
        this.pedido = new Pedido(descricao, universidade, this);
        System.out.println("Pedido realizado com sucesso!");
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

    public Pedido getPedido() {
        return pedido;
    }
}
