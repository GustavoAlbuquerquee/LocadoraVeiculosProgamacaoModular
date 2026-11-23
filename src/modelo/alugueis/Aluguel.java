package modelo.alugueis;

import modelo.veiculos.Veiculo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Aluguel {
    private static int proximoCodigo = 1;

    private final int codigo;
    private final String cliente;
    private final int quantidadeDias;
    private final List<Veiculo> veiculos = new ArrayList<>();

    protected Aluguel(String cliente, int quantidadeDias) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (quantidadeDias <= 0) {
            throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero.");
        }
        this.codigo = proximoCodigo++;
        this.cliente = cliente.trim();
        this.quantidadeDias = quantidadeDias;
    }

    public abstract String getTipo();

    public abstract boolean possuiQuilometragemIlimitada();

    public abstract boolean possuiSeguro();

    public abstract boolean possuiMotoristaParticular();

    protected String descricaoQuilometragem() {
        return possuiQuilometragemIlimitada() ? "ilimitada" : "limitada";
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo inválido.");
        }
        if (veiculos.contains(veiculo)) {
            throw new IllegalStateException("O veículo " + veiculo.getPlaca() + " já está associado a este aluguel.");
        }
        veiculo.alugar();
        veiculos.add(veiculo);
    }

    public List<Veiculo> getVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    public double calcularValorTotal() {
        double somaDiarias = 0;
        for (Veiculo veiculo : veiculos) {
            somaDiarias += veiculo.getValorDiaria();
        }
        return somaDiarias * quantidadeDias;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public int getQuantidadeDias() {
        return quantidadeDias;
    }

    public String getResumo() {
        return String.format("#%d - %s - Cliente: %s - %d dia(s) - %d veículo(s)",
                codigo, getTipo(), cliente, quantidadeDias, veiculos.size());
    }

    private static String simOuNao(boolean valor) {
        return valor ? "Sim" : "Não";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Aluguel #%d [%s] | Cliente: %s | Dias: %d", codigo, getTipo(), cliente, quantidadeDias));
        sb.append("\n   Quilometragem: ").append(descricaoQuilometragem())
          .append(" | Seguro incluso: ").append(simOuNao(possuiSeguro()))
          .append(" | Motorista particular: ").append(simOuNao(possuiMotoristaParticular()));
        sb.append("\n   Veículos:");
        if (veiculos.isEmpty()) {
            sb.append(" nenhum veículo associado");
        } else {
            for (Veiculo veiculo : veiculos) {
                sb.append("\n      - ").append(veiculo.getResumo());
            }
        }
        sb.append(String.format("\n   Valor total: R$ %.2f", calcularValorTotal()));
        return sb.toString();
    }
}
