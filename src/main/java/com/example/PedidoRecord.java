package com.example;

public class PedidoRecord {
    private Long numero;              // PK (int8)
    private String data;              // date (yyyy-MM-dd)
    private Double valor;             // float8
    private Boolean flagUniversidade; // bool
    private String descricao;         // varchar
    private String objetivo;          // varchar
    private String status;            // varchar
    private Long solicitante;         // int8 (FK)

    public PedidoRecord() {}

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Boolean getFlagUniversidade() { return flagUniversidade; }
    public void setFlagUniversidade(Boolean flagUniversidade) { this.flagUniversidade = flagUniversidade; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getSolicitante() { return solicitante; }
    public void setSolicitante(Long solicitante) { this.solicitante = solicitante; }
}
