package com.example;

public class Funcionario {
    private int id;
    private String cpf;
    private String matricula;
    private double salario;
    private String cargo;
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    
    public Funcionario(int id, String cpf, String matricula, double salario, String cargo, String nome, String email,
            String senha, String endereco) {
        this.id = id;
        this.cpf = cpf;
        this.matricula = matricula;
        this.salario = salario;
        this.cargo = cargo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public Funcionario(int id, String matricula, String cargo, String nome, String email, String senha) {
        this.id = id;
        this.matricula = matricula;
        this.cargo = cargo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean login(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + "\n" + "Cargo: " + this.cargo + "\n"
                + "Matricula: " + this.matricula + "\n";
    }

}
