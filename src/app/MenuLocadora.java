package app;

import interfaces.Avaliavel;
import interfaces.Inspecionavel;
import modelo.alugueis.Aluguel;
import modelo.veiculos.Veiculo;
import sistema.Locadora;

import java.util.List;

public class MenuLocadora {
    private static final int OPCAO_SAIR = 0;

    private final Locadora locadora;
    private final LeitorConsole leitor;

    public MenuLocadora(Locadora locadora, LeitorConsole leitor) {
        this.locadora = locadora;
        this.leitor = leitor;
    }

    public void executar() {
        int opcao;
        do {
            exibirOpcoes();
            opcao = leitor.lerInteiro("Escolha uma opção: ");
            System.out.println();
            try {
                processarOpcao(opcao);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != OPCAO_SAIR);
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("============ LOCADORA DE VEÍCULOS ============");
        System.out.println("1 - Associar veículos a um aluguel");
        System.out.println("2 - Exibir todos os veículos cadastrados");
        System.out.println("3 - Exibir todos os aluguéis cadastrados");
        System.out.println("4 - Exibir apenas veículos disponíveis");
        System.out.println("5 - Avaliar (veículos e aluguéis avaliáveis)");
        System.out.println("6 - Realizar inspeção em veículos");
        System.out.println("7 - Remover veículos em manutenção");
        System.out.println("0 - Sair");
        System.out.println("==============================================");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                associarVeiculosAoAluguel();
                break;
            case 2:
                exibirVeiculos();
                break;
            case 3:
                exibirAlugueis();
                break;
            case 4:
                exibirVeiculosDisponiveis();
                break;
            case 5:
                avaliar();
                break;
            case 6:
                realizarInspecao();
                break;
            case 7:
                removerVeiculosEmManutencao();
                break;
            case OPCAO_SAIR:
                System.out.println("Sistema encerrado. Até logo!");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void associarVeiculosAoAluguel() {
        List<Aluguel> alugueis = locadora.listarAlugueis();
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel cadastrado.");
            return;
        }
        System.out.println("--- Aluguéis cadastrados ---");
        for (Aluguel aluguel : alugueis) {
            System.out.println(aluguel.getResumo());
        }
        int codigo = leitor.lerInteiro("Informe o código do aluguel: ");
        Aluguel aluguel = locadora.buscarAluguelPorCodigo(codigo);

        boolean associarOutro;
        do {
            List<Veiculo> disponiveis = locadora.listarVeiculosDisponiveis();
            if (disponiveis.isEmpty()) {
                System.out.println("Não há veículos disponíveis para associar.");
                return;
            }
            System.out.println("--- Veículos disponíveis ---");
            for (Veiculo veiculo : disponiveis) {
                System.out.println(veiculo.getResumo());
            }
            String placa = leitor.lerTexto("Informe a placa do veículo: ");
            try {
                locadora.associarVeiculoAoAluguel(aluguel.getCodigo(), placa);
                System.out.println("Veículo associado com sucesso! " + aluguel.getResumo());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            associarOutro = leitor.lerSimOuNao("Deseja associar outro veículo a este aluguel? (S/N): ");
        } while (associarOutro);
    }

    private void exibirVeiculos() {
        exibirListaDeVeiculos("Veículos cadastrados", locadora.listarVeiculos());
    }

    private void exibirAlugueis() {
        List<Aluguel> alugueis = locadora.listarAlugueis();
        System.out.println("--- Aluguéis cadastrados ---");
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel cadastrado.");
            return;
        }
        for (Aluguel aluguel : alugueis) {
            System.out.println(aluguel);
            System.out.println();
        }
    }

    private void exibirVeiculosDisponiveis() {
        exibirListaDeVeiculos("Veículos disponíveis", locadora.listarVeiculosDisponiveis());
    }

    private void exibirListaDeVeiculos(String titulo, List<Veiculo> veiculos) {
        System.out.println("--- " + titulo + " ---");
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo encontrado.");
            return;
        }
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }
    }

    private void avaliar() {
        List<Avaliavel> avaliaveis = locadora.listarAvaliaveis();
        if (avaliaveis.isEmpty()) {
            System.out.println("Nenhum item avaliável cadastrado.");
            return;
        }
        System.out.println("--- Itens avaliáveis ---");
        for (int i = 0; i < avaliaveis.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + avaliaveis.get(i));
        }
        int escolha = leitor.lerInteiro("Escolha o item que deseja avaliar: ");
        if (escolha < 1 || escolha > avaliaveis.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Avaliavel item = avaliaveis.get(escolha - 1);
        int nota = leitor.lerInteiro("Informe a nota (" + Avaliavel.NOTA_MINIMA + " a " + Avaliavel.NOTA_MAXIMA + "): ");
        item.avaliar(nota);
        System.out.println("Avaliação registrada com sucesso!");
        System.out.println(item);
    }

    private void realizarInspecao() {
        List<Inspecionavel> inspecionaveis = locadora.listarInspecionaveis();
        if (inspecionaveis.isEmpty()) {
            System.out.println("Nenhum veículo inspecionável cadastrado.");
            return;
        }
        System.out.println("--- Veículos inspecionáveis ---");
        for (int i = 0; i < inspecionaveis.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + inspecionaveis.get(i));
        }
        int escolha = leitor.lerInteiro("Escolha o veículo que deseja inspecionar: ");
        if (escolha < 1 || escolha > inspecionaveis.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Inspecionavel item = inspecionaveis.get(escolha - 1);
        item.realizarInspecao();
        System.out.println("Inspeção realizada!");
        if (item instanceof Veiculo && ((Veiculo) item).estaEmManutencao()) {
            System.out.println("Resultado: REPROVADO - o veículo passou do intervalo de revisão e foi enviado para manutenção.");
        } else {
            System.out.println("Resultado: APROVADO - o veículo está disponível para aluguel.");
        }
        System.out.println(item);
    }

    private void removerVeiculosEmManutencao() {
        List<Veiculo> removidos = locadora.removerVeiculosEmManutencao();
        if (removidos.isEmpty()) {
            System.out.println("Nenhum veículo em manutenção para remover.");
            return;
        }
        System.out.println(removidos.size() + " veículo(s) em manutenção removido(s) do sistema:");
        for (Veiculo veiculo : removidos) {
            System.out.println(" - " + veiculo.getResumo());
        }
    }
}
