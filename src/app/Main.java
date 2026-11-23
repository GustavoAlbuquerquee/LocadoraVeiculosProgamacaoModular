package app;

import modelo.alugueis.Aluguel;
import modelo.alugueis.AluguelBasico;
import modelo.alugueis.AluguelExecutivo;
import modelo.veiculos.Carro;
import modelo.veiculos.Moto;
import modelo.veiculos.Van;
import sistema.Locadora;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Locadora locadora = new Locadora();
        criarDadosIniciais(locadora);

        try (Scanner scanner = new Scanner(System.in)) {
            MenuLocadora menu = new MenuLocadora(locadora, new LeitorConsole(scanner));
            menu.executar();
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Entrada de dados encerrada. Sistema finalizado.");
        }
    }

    private static void criarDadosIniciais(Locadora locadora) {
        Carro carro = new Carro("ABC1D23", "Fiat Argo", 2022, 150.00, 25000, 4);
        Moto moto = new Moto("MTO2E45", "Honda CG 160", 2020, 80.00, 18000, 160);
        Van van = new Van("VAN3F67", "Mercedes-Benz Sprinter", 2023, 350.00, 8000, 15);

        locadora.cadastrarVeiculo(carro);
        locadora.cadastrarVeiculo(moto);
        locadora.cadastrarVeiculo(van);

        Aluguel aluguelBasico = new AluguelBasico("Ana Souza", 3);
        Aluguel aluguelExecutivo = new AluguelExecutivo("Bruno Lima", 5);

        locadora.cadastrarAluguel(aluguelBasico);
        locadora.cadastrarAluguel(aluguelExecutivo);

        aluguelBasico.adicionarVeiculo(carro);
    }
}
