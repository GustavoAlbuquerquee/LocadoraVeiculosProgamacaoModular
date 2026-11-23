package modelo.alugueis;

public class AluguelPremium extends Aluguel {

    public AluguelPremium(String cliente, int quantidadeDias) {
        super(cliente, quantidadeDias);
    }

    @Override
    public String getTipo() {
        return "Premium";
    }

    @Override
    public boolean possuiQuilometragemIlimitada() {
        return true;
    }

    @Override
    public boolean possuiSeguro() {
        return true;
    }

    @Override
    public boolean possuiMotoristaParticular() {
        return false;
    }
}
