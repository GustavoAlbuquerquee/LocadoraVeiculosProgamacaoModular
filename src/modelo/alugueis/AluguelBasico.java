package modelo.alugueis;

public class AluguelBasico extends Aluguel {
    public static final int LIMITE_KM_POR_DIA = 100;

    public AluguelBasico(String cliente, int quantidadeDias) {
        super(cliente, quantidadeDias);
    }

    @Override
    public String getTipo() {
        return "Básico";
    }

    @Override
    public boolean possuiQuilometragemIlimitada() {
        return false;
    }

    @Override
    public boolean possuiSeguro() {
        return false;
    }

    @Override
    public boolean possuiMotoristaParticular() {
        return false;
    }

    public int getLimiteKmPorDia() {
        return LIMITE_KM_POR_DIA;
    }

    @Override
    protected String descricaoQuilometragem() {
        return "até " + LIMITE_KM_POR_DIA + " km por dia";
    }
}
