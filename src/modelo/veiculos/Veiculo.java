package modelo.veiculos;

import enums.StatusVeiculo;

import java.time.Year;

public abstract class Veiculo {
    private static final int ANO_MINIMO = 1900;

    private final String placa;
    private final String modelo;
    private final int anoFabricacao;
    private final double valorDiaria;
    private final int quilometragem;
    private StatusVeiculo status;

    protected Veiculo(String placa, String modelo, int anoFabricacao, double valorDiaria, int quilometragem) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("A placa é obrigatória.");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("O modelo é obrigatório.");
        }
        int anoMaximo = Year.now().getValue() + 1;
        if (anoFabricacao < ANO_MINIMO || anoFabricacao > anoMaximo) {
            throw new IllegalArgumentException("Ano de fabricação inválido: " + anoFabricacao + ".");
        }
        if (valorDiaria <= 0) {
            throw new IllegalArgumentException("O valor da diária deve ser maior que zero.");
        }
        if (quilometragem < 0) {
            throw new IllegalArgumentException("A quilometragem não pode ser negativa.");
        }
        this.placa = placa.trim().toUpperCase();
        this.modelo = modelo.trim();
        this.anoFabricacao = anoFabricacao;
        this.valorDiaria = valorDiaria;
        this.quilometragem = quilometragem;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public abstract String getTipo();

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public boolean estaDisponivel() {
        return status == StatusVeiculo.DISPONIVEL;
    }

    public boolean estaAlugado() {
        return status == StatusVeiculo.ALUGADO;
    }

    public boolean estaEmManutencao() {
        return status == StatusVeiculo.MANUTENCAO;
    }

    public void alugar() {
        if (!estaDisponivel()) {
            throw new IllegalStateException("O veículo " + placa + " não está disponível (status: " + status + ").");
        }
        status = StatusVeiculo.ALUGADO;
    }

    protected void enviarParaManutencao() {
        if (estaAlugado()) {
            throw new IllegalStateException("O veículo " + placa + " está alugado e não pode ir para manutenção.");
        }
        status = StatusVeiculo.MANUTENCAO;
    }

    protected void liberarParaAluguel() {
        if (estaAlugado()) {
            throw new IllegalStateException("O veículo " + placa + " está alugado.");
        }
        status = StatusVeiculo.DISPONIVEL;
    }

    public String getResumo() {
        return String.format("%s %s (%s) - R$ %.2f/dia - %s", getTipo(), modelo, placa, valorDiaria, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Veiculo)) {
            return false;
        }
        return placa.equals(((Veiculo) obj).placa);
    }

    @Override
    public int hashCode() {
        return placa.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s | Placa: %s | Modelo: %s | Ano: %d | Diária: R$ %.2f | Km: %d | Status: %s",
                getTipo(), placa, modelo, anoFabricacao, valorDiaria, quilometragem, status);
    }
}
