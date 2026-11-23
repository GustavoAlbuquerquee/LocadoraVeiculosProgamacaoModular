package modelo.registros;

import interfaces.Avaliavel;

public class RegistroAvaliacoes {
    private int somaNotas;
    private int quantidade;

    public void registrar(int nota) {
        if (nota < Avaliavel.NOTA_MINIMA || nota > Avaliavel.NOTA_MAXIMA) {
            throw new IllegalArgumentException("A nota deve estar entre "
                    + Avaliavel.NOTA_MINIMA + " e " + Avaliavel.NOTA_MAXIMA + ".");
        }
        somaNotas += nota;
        quantidade++;
    }

    public double getMedia() {
        return quantidade == 0 ? 0 : (double) somaNotas / quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        if (quantidade == 0) {
            return "sem avaliações";
        }
        return String.format("média %.1f (%d avaliação(ões))", getMedia(), quantidade);
    }
}
