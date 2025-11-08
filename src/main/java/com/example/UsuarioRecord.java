package com.example;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo 1:1 com a tabela "Usuario" do Supabase.
 * Campos (conforme seu print): id, cpf/cnpj, nome, email, endereco,
 * flagReitor, flagSolicitante, flagContemplado, noPedido, dtPedido,
 * universidade, dtContemplacao.
 *
 * Datas como String "YYYY-MM-DD" para não exigir JavaTimeModule.
 */
public class UsuarioRecord {

    private Long id;                 // int8 (PK)

    @JsonProperty("cpf/cnpj")
    private String cpfCnpj;          // varchar (tem "/" no nome da coluna)

    private String nome;             // varchar
    private String email;            // varchar
    private String endereco;         // varchar

    private Boolean flagReitor;         // bool
    private Boolean flagSolicitante;    // bool
    private Boolean flagContemplado;    // bool

    private Long noPedido;           // int8 (FK opcional)
    private String dtPedido;         // date (YYYY-MM-DD)
    private String universidade;     // varchar

    @JsonProperty("dtContemplacao")
    private Date dtContemplacao;   // date (YYYY-MM-DD)

    public UsuarioRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Boolean getFlagReitor() { return flagReitor; }
    public void setFlagReitor(Boolean flagReitor) { this.flagReitor = flagReitor; }

    public Boolean getFlagSolicitante() { return flagSolicitante; }
    public void setFlagSolicitante(Boolean flagSolicitante) { this.flagSolicitante = flagSolicitante; }

    public Boolean getFlagContemplado() { return flagContemplado; }
    public void setFlagContemplado(Boolean flagContemplado) { this.flagContemplado = flagContemplado; }

    public Long getNoPedido() { return noPedido; }
    public void setNoPedido(Long noPedido) { this.noPedido = noPedido; }

    public String getDtPedido() { return dtPedido; }
    public void setDtPedido(String dtPedido) { this.dtPedido = dtPedido; }

    public String getUniversidade() { return universidade; }
    public void setUniversidade(String universidade) { this.universidade = universidade; }

    public Date getDtContemplacao() { return dtContemplacao; }
    public void setDtContemplacao(Date dtContemplacao) { this.dtContemplacao = dtContemplacao; }
}
