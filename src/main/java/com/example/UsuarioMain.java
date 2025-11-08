package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuarioMain {
    public static void main(String[] args) {
        List<Usuario> usuarios = new ArrayList<>();
        List<Solicitante> solicitantes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        // Usuários iniciais
        usuarios.add(new Usuario(1, "leandro", "000", "admin@gmail.com", "Av. Brasil", "123", true));
        usuarios.add(new Usuario(2, "fabricio", "111", "teste@gmail.com", "Rua Central", "123", false));

        int opcao;
        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("(1) Cadastrar novo usuário");
            System.out.println("(2) Fazer login");


            System.out.println("(0) Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF/CNPJ: ");
                    String cpfCnpj = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    System.out.print("É reitor? (true/false): ");
                    boolean reitor = Boolean.parseBoolean(sc.nextLine());

                    usuarios.add(new Usuario(id, nome, cpfCnpj, email, endereco, senha, reitor));
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.print("Email: ");
                    String emailLogin = sc.nextLine();
                    System.out.print("Senha: ");
                    String senhaLogin = sc.nextLine();

                    Usuario usuarioLogado = null;
                    for (Usuario u : usuarios) {
                        if (u.login(emailLogin, senhaLogin)) {
                            usuarioLogado = u;
                            break;
                        }
                    }

                    if (usuarioLogado != null) {
                        System.out.println("\nBem-vindo, " + usuarioLogado.getNome() + "!");

                        Solicitante solicitanteAtual = null;
                        for (Solicitante s : solicitantes) {
                            if (s.getEmail().equals(usuarioLogado.getEmail())) {
                                solicitanteAtual = s;
                                break;
                            }
                        }

                        int opcaoLogin;
                        do {
                            System.out.println("\n=== MENU DO USUÁRIO ===");
                            if (usuarioLogado.isFlagReitor()) {
                                System.out.println("(1) Verificar pedidos");
                            } else {
                                System.out.println("(1) Fazer pedido");
                                if (solicitanteAtual != null && solicitanteAtual.getPedido() != null) {
                                    System.out.println("(2) Consultar status do pedido");

                                    // ainda não consegui acessar um usuário contemplado pra testar
                                    if ("Aprovado".equalsIgnoreCase(solicitanteAtual.getPedido().getStatus())) {
                                        System.out.println("(3) Acessar dados");
                                        System.out.println("(4) Filtrar dados");
                                        System.out.println("(5) Exportar dados");
                                    }
                                }
                            }
                            System.out.println("(0) Logout");
                            System.out.print("Escolha uma opção: ");
                            opcaoLogin = Integer.parseInt(sc.nextLine());

                            switch (opcaoLogin) {
                                case 1:
                                    if (usuarioLogado.isFlagReitor()) {
                                        System.out.println("\nPedidos registrados:");
                                        boolean existePedido = false;
                                        for (Solicitante s : solicitantes) {
                                            Pedido p = s.getPedido();
                                            if (p != null) {
                                                existePedido = true;
                                                System.out.println("Usuário: " + s.getNome() +
                                                        ", Universidade: " + p.getUniversidade() +
                                                        ", Pedido: " + p.getDescricao() +
                                                        ", Status: " + p.getStatus() +
                                                        ", Data: " + p.getDataPedido());
                                            }
                                        }
                                        if (!existePedido) {
                                            System.out.println("Nenhum pedido encontrado.");
                                        }

                                        System.out.print("Deseja aprovar algum pedido? (s/n): ");
                                        String aprovar = sc.nextLine();
                                        if (aprovar.equalsIgnoreCase("s")) {
                                            System.out.print("Digite o email do solicitante para aprovar: ");
                                            String emailAprovar = sc.nextLine();
                                            for (Solicitante s : solicitantes) {
                                                if (s.getEmail().equals(emailAprovar) && s.getPedido() != null) {
                                                    s.getPedido().aprovar();
                                                    System.out.println("Pedido aprovado!");
                                                    break;
                                                }
                                            }
                                        }

                                    } else {
                                        if (solicitanteAtual == null) {
                                            solicitanteAtual = new Solicitante(usuarioLogado);
                                            solicitantes.add(solicitanteAtual);
                                        }

                                        System.out.print("Digite o pedido: ");
                                        String descricao = sc.nextLine();
                                        System.out.print("Digite a universidade: ");
                                        String universidade = sc.nextLine();

                                        solicitanteAtual.fazerPedido(descricao, universidade);
                                    }
                                    break;

                                case 2:
                                    if (!usuarioLogado.isFlagReitor() && solicitanteAtual != null
                                            && solicitanteAtual.getPedido() != null) {
                                        String status = solicitanteAtual.consultarStatus();
                                        System.out.println("Status do pedido: " + status);
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                    break;

                                case 3:
                                    if (solicitanteAtual != null && solicitanteAtual.getPedido() != null
                                            && "Aprovado".equalsIgnoreCase(solicitanteAtual.getPedido().getStatus())) {
                                        System.out.println(" Acessando dados do pedido contemplado...");
                                        // a fazer
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                    break;

                                case 4:
                                    if (solicitanteAtual != null && solicitanteAtual.getPedido() != null
                                            && "Aprovado".equalsIgnoreCase(solicitanteAtual.getPedido().getStatus())) {
                                        System.out.println(" Filtrando dados...");
                                        // a fazer
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                    break;

                                case 5:
                                    if (solicitanteAtual != null && solicitanteAtual.getPedido() != null
                                            && "Aprovado".equalsIgnoreCase(solicitanteAtual.getPedido().getStatus())) {
                                        System.out.println("Exportando dados...");
                                        // a fazer
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                    break;

                                case 0:
                                    System.out.println("Logout realizado.");
                                    break;

                                default:
                                    System.out.println("Opção inválida!");
                            }

                        } while (opcaoLogin != 0);

                    } else {
                        System.out.println("Login inválido!");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}
