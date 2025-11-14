package com.example;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private UsuarioDAO userDAO = new UsuarioDAO(System.getenv("SUPABASE_URL"), System.getenv("SUPABASE_KEY"));
    private PedidoDAO pedidoDAO = new PedidoDAO(System.getenv("SUPABASE_URL"), System.getenv("SUPABASE_KEY"));
    private FuncionarioDAO funcDAO = new FuncionarioDAO(System.getenv("SUPABASE_URL"), System.getenv("SUPABASE_KEY"));
    private UsuarioRecord usuarioLogado;
    private Funcionario funcionarioLogado;

    public void menuInicial() {
        System.out.println("----------SEJA BEM-VINDO AO ANP SUCKS PROJECT------------");
        System.out.println("Selecione o tipo de usuário:");
        System.out.println("1. Funcionário ANP");
        System.out.println("2. Usuário");
        System.out.println("3. Sair do sistema");
    }

    public UsuarioRecord cadastroSolicitante(Scanner input) {
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("CPF/CNPJ: ");
        String cpfCnpj = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        System.out.print("Universidade: ");
        String universidade = input.nextLine();
        UsuarioRecord user = new UsuarioRecord(nome, cpfCnpj, email, senha, true, universidade);
        userDAO.criar(user);
        return user;
    }

    public boolean loginUsuario(Scanner input) {
        System.out.println("----------LOGIN USUÁRIO----------");
        System.out.print("E-mail: ");
        String usuario = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        String tipoUsuario = "";
        UsuarioRecord user = userDAO.buscarPorEmail(usuario);
        if (user != null && user.getSenha().equals(senha)) {
            if (user.getFlagSolicitante() != null && user.getFlagSolicitante()) {
                tipoUsuario = "Solicitante";
            } else {
                tipoUsuario = "Contemplado";
            }
            this.usuarioLogado = user;
        } else {
            System.out.println("Usuário ou senha inválidos.");
            return false;
        }
        System.out.println("Você é um usuário do tipo: " + tipoUsuario);
        return true;
    }

    public boolean loginFuncionario(Scanner input) {
        System.out.println("----------LOGIN FUNCIONÁRIO----------");
        System.out.print("E-mail: ");
        String usuario = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        Funcionario func = funcDAO.buscarPorEmail(usuario);
        if (func != null && func.getSenha().equals(senha)) {
            System.out.println("Login bem-sucedido como Funcionário ANP.");
            this.funcionarioLogado = func;
        } else {
            System.out.println("Usuário ou senha inválidos.");
            return false;
        }
        return true;
    }

    public int menuSolicitante(Scanner input) {
        int opcao = 0;
        System.out.println("--------------------MENU SOLICITANTE--------------------");
        while (opcao != 3) {
            try {
                System.out.println("1. Cadastrar novo pedido");
                System.out.println("2. Consultar status do pedido");
                System.out.println("3. Voltar para o menu inicial");
                String entrada = input.nextLine();
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                continue;
            }
            switch (opcao) {
                case 1:
                    try {
                        System.out.print("Data do pedido (YYYY-MM-DD): ");
                        String data = input.nextLine();
                        System.out.print("Objetivo do pedido: ");
                        String objetivo = input.nextLine();
                        System.out.print("Descrição do pedido: ");
                        String descricao = input.nextLine();
                        PedidoRecord pedidoRecord = new PedidoRecord(data, (Double) 0.0, true, descricao, objetivo,
                                "PENDENTE", usuarioLogado.getId());
                        pedidoDAO.criar(pedidoRecord);
                        List<PedidoRecord> list_resultado = pedidoDAO.buscarPorSolicitante(usuarioLogado.getId());
                        System.out.println("Pedidos do solicitante (anote o número dos pedidos):");
                        System.out.println(list_resultado);
                    } catch (Exception e) {
                        System.out.println("Erro ao criar pedido: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Qual o número do pedido?");
                        String numeroStr = input.nextLine();
                        long numero = Long.parseLong(numeroStr);
                        PedidoRecord resultado = pedidoDAO.buscarPorNumero(numero);
                        if (resultado == null) {
                            System.out.println("Pedido não encontrado.");
                            break;
                        } else {
                            if (!resultado.getSolicitante().equals(usuarioLogado.getId())) {
                                System.out.println("Pedido não pertence ao usuário.");
                                break;
                            }
                            System.out.println("Status do pedido: " + resultado.getStatus());
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar pedido: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
        return opcao;
    }

    public int menuContemplado(Scanner input) {
        int opcao = 0;
        System.out.println("--------------------MENU CONTEMPLADO--------------------");
        while (opcao != 3) {
            try {
                System.out.println("1. Consultar dados da ANP");
                System.out.println("2. Exportar os dados da ANP (.csv)");
                System.out.println("3. Voltar para o menu inicial");
                String entrada = input.nextLine();
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                continue;
            }
            switch (opcao) {
                case 1:
                    System.out.println("IMAGINE OS DADOS DA ANP AQUI...");
                    break;
                case 2:
                    System.out.println("IMAGINE OS DADOS DA ANP SENDO EXPORTADOS...");
                    break;
                case 3:
                    System.out.println("Retornando ao menu inicial...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
        return opcao;
    }

    public int menuFuncionario(Scanner input) {
        int opcao = 0;
        System.out.println("--------------------MENU FUNCIONÁRIO--------------------");
        while (opcao != 3) {
            try {
                System.out.println("1. Consultar pedidos");
                System.out.println("2. Aprovar pedido");
                System.out.println("3. Voltar para o menu inicial");
                String entrada = input.nextLine();
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                continue;
            }
            switch (opcao) {
                case 1:
                    List<PedidoRecord> listaPedidos = pedidoDAO.listarTodos();
                    System.out.println("Lista de pedidos:");
                    for (PedidoRecord pedido : listaPedidos) {
                        System.out.println("Pedido (" + pedido.getNumero() + ")");
                        System.out.println(pedido);
                        UsuarioRecord solicitante = userDAO.buscarPorId(pedido.getSolicitante());
                        System.out.println(
                                "Solicitante: " + solicitante.getNome() + " - " + solicitante.getUniversidade());
                        System.out.println("-------------------------------");
                    }
                    break;
                case 2:
                    System.out.println("Qual o número do pedido?");
                    String numeroStr = input.nextLine();
                    long numero = Long.parseLong(numeroStr);
                    PedidoRecord resultado = pedidoDAO.buscarPorNumero(numero);
                    if (resultado != null && resultado.getStatus().equals("PENDENTE")) {
                        resultado.setStatus("APROVADO");
                        boolean atualizado = pedidoDAO.atualizar(numero, resultado);
                        if (atualizado) {
                            System.out.println("Pedido aprovado com sucesso!");
                            UsuarioRecord usuario = userDAO.buscarPorId(resultado.getSolicitante());
                            usuario.setFlagContemplado(true);
                            usuario.setFlagSolicitante(false);
                            userDAO.atualizar(usuario.getId(), usuario);
                        } else {
                            System.out.println("Falha ao aprovar o pedido.");
                        }
                    } else {
                        System.out.println("Pedido não encontrado.");
                    }
                    break;
                case 3:
                    System.out.println("");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
        return opcao;
    }

    public void loop() {
        Scanner input = new Scanner(System.in);
        int opcaoInicial = 0;
        while (opcaoInicial != 3) {
            menuInicial();
            String entrada = input.nextLine();
            opcaoInicial = Integer.parseInt(entrada);
            switch (opcaoInicial) {
                case 1:
                    if (loginFuncionario(input)) {
                        menuFuncionario(input);
                    }
                    break;
                case 2:
                    if (loginUsuario(input)) {
                        UsuarioRecord user = userDAO.buscarPorEmail(usuarioLogado.getEmail());
                        if (user.getFlagSolicitante() != null && user.getFlagSolicitante()) {
                            menuSolicitante(input);
                        } else {
                            menuContemplado(input);
                        }
                    } else {
                        System.out.println("Deseja se cadastrar como solicitante? (s/n)");
                        String resposta = input.nextLine();
                        if (resposta.equalsIgnoreCase("s")) {
                            cadastroSolicitante(input);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
        input.close();
    }
}
