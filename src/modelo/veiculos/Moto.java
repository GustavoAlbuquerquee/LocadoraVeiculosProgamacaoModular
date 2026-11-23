package modelo.veiculos;

import interfaces.Inspecionavel;
import modelo.registros.RegistroInspecoes;

public class Moto extends Veiculo implements Inspecionavel {
    private static final int INTERVALO_REVISAO_KM = 5000;

    private final int cilindrada;
    private final RegistroInspecoes inspecoes = new RegistroInspecoes(INTERVALO_REVISAO_KM);

    public Moto(String placa, String modelo, int anoFabricacao, double valorDiaria, int quilometragem,
                int cilindrada) {
        super(placa, modelo, anoFabricacao, valorDiaria, quilometragem);
        if (cilindrada <= 0) {
            throw new IllegalArgumentException("A cilindrada deve ser maior que zero.");
        }
        this.cilindrada = cilindrada;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    @Override
    public void realizarInspecao() {
        if (estaAlugado()) {
            throw new IllegalStateException("A moto " + getPlaca() + " está alugada e não pode ser inspecionada.");
        }
        boolean precisaManutencao = inspecoes.registrar(getQuilometragem());
        if (precisaManutencao) {
            enviarParaManutencao();
        } else {
            liberarParaAluguel();
        }
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
    public String toString() {
        return super.toString() + " | Cilindrada: " + cilindrada + " cc | Inspeção: " + inspecoes;
    }
}
