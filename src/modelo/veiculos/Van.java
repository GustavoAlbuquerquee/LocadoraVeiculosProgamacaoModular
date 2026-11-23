package modelo.veiculos;

import interfaces.Avaliavel;
import interfaces.Inspecionavel;
import modelo.registros.RegistroAvaliacoes;
import modelo.registros.RegistroInspecoes;

public class Van extends Veiculo implements Avaliavel, Inspecionavel {
    private static final int INTERVALO_REVISAO_KM = 10000;

    private final int capacidadeMaximaPassageiros;
    private final RegistroAvaliacoes avaliacoes = new RegistroAvaliacoes();
    private final RegistroInspecoes inspecoes = new RegistroInspecoes(INTERVALO_REVISAO_KM);

    public Van(String placa, String modelo, int anoFabricacao, double valorDiaria, int quilometragem,
               int capacidadeMaximaPassageiros) {
        super(placa, modelo, anoFabricacao, valorDiaria, quilometragem);
        if (capacidadeMaximaPassageiros <= 0) {
            throw new IllegalArgumentException("A capacidade máxima de passageiros deve ser maior que zero.");
        }
        this.capacidadeMaximaPassageiros = capacidadeMaximaPassageiros;
    }

    @Override
    public String getTipo() {
        return "Van";
    }

    @Override
    public void avaliar(int nota) {
        avaliacoes.registrar(nota);
    }

    @Override
    public void realizarInspecao() {
        if (estaAlugado()) {
            throw new IllegalStateException("A van " + getPlaca() + " está alugada e não pode ser inspecionada.");
        }
        boolean precisaManutencao = inspecoes.registrar(getQuilometragem());
        if (precisaManutencao) {
            enviarParaManutencao();
        } else {
            liberarParaAluguel();
        }
    }

    public int getCapacidadeMaximaPassageiros() {
        return capacidadeMaximaPassageiros;
    }

    public double getMediaAvaliacoes() {
        return avaliacoes.getMedia();
    }

    @Override
    public String toString() {
        return super.toString() + " | Passageiros: " + capacidadeMaximaPassageiros
                + " | Avaliação: " + avaliacoes + " | Inspeção: " + inspecoes;
    }
}
