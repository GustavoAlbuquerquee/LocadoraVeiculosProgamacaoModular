package sistema;

import interfaces.Avaliavel;
import interfaces.Inspecionavel;
import modelo.alugueis.Aluguel;
import modelo.veiculos.Veiculo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Locadora {
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final List<Aluguel> alugueis = new ArrayList<>();

    public void cadastrarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo inválido.");
        }
        if (veiculos.contains(veiculo)) {
            throw new IllegalArgumentException("Já existe um veículo com a placa " + veiculo.getPlaca() + ".");
        }
        veiculos.add(veiculo);
    }

    public void cadastrarAluguel(Aluguel aluguel) {
        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel inválido.");
        }
        alugueis.add(aluguel);
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        if (placa != null) {
            for (Veiculo veiculo : veiculos) {
                if (veiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                    return veiculo;
                }
            }
        }
        throw new IllegalArgumentException("Veículo com placa " + placa + " não encontrado.");
    }

    public Aluguel buscarAluguelPorCodigo(int codigo) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getCodigo() == codigo) {
                return aluguel;
            }
        }
        throw new IllegalArgumentException("Aluguel #" + codigo + " não encontrado.");
    }

    public void associarVeiculoAoAluguel(int codigoAluguel, String placa) {
        Aluguel aluguel = buscarAluguelPorCodigo(codigoAluguel);
        Veiculo veiculo = buscarVeiculoPorPlaca(placa);
        aluguel.adicionarVeiculo(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    public List<Aluguel> listarAlugueis() {
        return Collections.unmodifiableList(alugueis);
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.estaDisponivel()) {
                disponiveis.add(veiculo);
            }
        }
        return disponiveis;
    }

    public List<Avaliavel> listarAvaliaveis() {
        List<Avaliavel> avaliaveis = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo instanceof Avaliavel) {
                avaliaveis.add((Avaliavel) veiculo);
            }
        }
        for (Aluguel aluguel : alugueis) {
            if (aluguel instanceof Avaliavel) {
                avaliaveis.add((Avaliavel) aluguel);
            }
        }
        return avaliaveis;
    }

    public List<Inspecionavel> listarInspecionaveis() {
        List<Inspecionavel> inspecionaveis = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo instanceof Inspecionavel) {
                inspecionaveis.add((Inspecionavel) veiculo);
            }
        }
        return inspecionaveis;
    }

    public List<Veiculo> removerVeiculosEmManutencao() {
        List<Veiculo> removidos = new ArrayList<>();
        Iterator<Veiculo> iterator = veiculos.iterator();
        while (iterator.hasNext()) {
            Veiculo veiculo = iterator.next();
            if (veiculo.estaEmManutencao()) {
                removidos.add(veiculo);
                iterator.remove();
            }
        }
        return removidos;
    }
}
