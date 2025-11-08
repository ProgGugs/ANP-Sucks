package com.example;

import java.util.Scanner;

public class Menu {
    public void menuInicial() {
        System.out.println("----------WELCOME TO THE ANP SUCKS PROJECT------------");
        
    }
    public void menuSolicitante() {
        Scanner input = new Scanner(System.in);
                int opcao = 0;
        while (opcao >= 0) {
            String entrada = input.nextLine();
            opcao = Integer.parseInt(entrada);
            switch (opcao) {
                case 1:
                    
                    break;
                case 2:
                    break;
                case 3:

                    break;
                case 4:

                    break;
                default:

                    break;
            }
        }
        input.close();
    }
}
