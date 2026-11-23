package modelo.alugueis;

import interfaces.Avaliavel;
import modelo.registros.RegistroAvaliacoes;

public class AluguelExecutivo extends Aluguel implements Avaliavel {
    private final RegistroAvaliacoes avaliacoes = new RegistroAvaliacoes();

    public AluguelExecutivo(String cliente, int quantidadeDias) {
        super(cliente, quantidadeDias);
    }

    @Override
    public String getTipo() {
        return "Executivo";
    }

    @Override
    public boolean possuiQuilometragemIlimitada() {
        return true;
    }

    @Override
    public boolean possuiSeguro() {
        return false;
    }

    @Override
    public boolean possuiMotoristaParticular() {
        return true;
    }

    @Override
    public void avaliar(int nota) {
        avaliacoes.registrar(nota);
    }

    public double getMediaAvaliacoes() {
        return avaliacoes.getMedia();
    }

    @Override
    public String toString() {
        return super.toString() + "\n   Avaliação: " + avaliacoes;
    }
}
