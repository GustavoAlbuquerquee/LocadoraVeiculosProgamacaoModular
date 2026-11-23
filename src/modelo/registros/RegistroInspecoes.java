package modelo.registros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroInspecoes {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final int intervaloRevisaoKm;
    private int quantidade;
    private int kmUltimaInspecao;
    private LocalDate dataUltimaInspecao;

    public RegistroInspecoes(int intervaloRevisaoKm) {
        if (intervaloRevisaoKm <= 0) {
            throw new IllegalArgumentException("O intervalo de revisão deve ser maior que zero.");
        }
        this.intervaloRevisaoKm = intervaloRevisaoKm;
    }

    public boolean registrar(int quilometragemAtual) {
        boolean precisaManutencao = quilometragemAtual - kmUltimaInspecao > intervaloRevisaoKm;
        quantidade++;
        kmUltimaInspecao = quilometragemAtual;
        dataUltimaInspecao = LocalDate.now();
        return precisaManutencao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getIntervaloRevisaoKm() {
        return intervaloRevisaoKm;
    }

    @Override
    public String toString() {
        if (quantidade == 0) {
            return "nunca inspecionado";
        }
        return quantidade + " inspeção(ões), última em " + dataUltimaInspecao.format(FORMATO_DATA);
    }
}
