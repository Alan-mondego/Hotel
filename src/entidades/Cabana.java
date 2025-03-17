package entidades;

import java.math.BigDecimal;

public class Cabana extends Hospedagem {
    private boolean possuiLareira;
    private boolean possuiVistaMar;

    public Cabana(String identificacao, int capacidadeMaxima, BigDecimal precoDiaria,
                  boolean possuiLareira, boolean possuiVistaMar) {
        super(identificacao, capacidadeMaxima, precoDiaria);
        this.possuiLareira = possuiLareira;
        this.possuiVistaMar = possuiVistaMar;
    }

    @Override
    public BigDecimal calcularValorHospedagem(int numeroDiarias) {
        BigDecimal valorBase = getPrecoDiaria().multiply(BigDecimal.valueOf(numeroDiarias));
        
        // Adicional de 15% para cabanas com lareira
        if (possuiLareira) {
            valorBase = valorBase.multiply(BigDecimal.valueOf(1.15));
        }
        
        // Adicional de 25% para cabanas com vista para o mar
        if (possuiVistaMar) {
            valorBase = valorBase.multiply(BigDecimal.valueOf(1.25));
        }
        
        return valorBase;
    }

    public boolean isPossuiLareira() {
        return possuiLareira;
    }

    public boolean isPossuiVistaMar() {
        return possuiVistaMar;
    }
} 