package modelo.veiculos;

import interfaces.Avaliavel;
import modelo.registros.RegistroAvaliacoes;

public class Carro extends Veiculo implements Avaliavel {
    private final int quantidadePortas;
    private final RegistroAvaliacoes avaliacoes = new RegistroAvaliacoes();

    public Carro(String placa, String modelo, int anoFabricacao, double valorDiaria, int quilometragem,
                 int quantidadePortas) {
        super(placa, modelo, anoFabricacao, valorDiaria, quilometragem);
        if (quantidadePortas <= 0) {
            throw new IllegalArgumentException("A quantidade de portas deve ser maior que zero.");
        }
        this.quantidadePortas = quantidadePortas;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public void avaliar(int nota) {
        avaliacoes.registrar(nota);
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public double getMediaAvaliacoes() {
        return avaliacoes.getMedia();
    }

    @Override
    public String toString() {
        return super.toString() + " | Portas: " + quantidadePortas + " | Avaliação: " + avaliacoes;
    }
}
