package entidades;

import java.math.BigDecimal;

public class Quarto extends Hospedagem {
    private TipoQuarto tipo;

    public enum TipoQuarto {
        STANDARD,
        LUXO,
        SUITE_PRESIDENCIAL
    }

    public Quarto(String identificacao, int capacidadeMaxima, BigDecimal precoDiaria, TipoQuarto tipo) {
        super(identificacao, capacidadeMaxima, precoDiaria);
        this.tipo = tipo;
    }

    @Override
    public BigDecimal calcularValorHospedagem(int numeroDiarias) {
        BigDecimal valorBase = getPrecoDiaria().multiply(BigDecimal.valueOf(numeroDiarias));
        
        // Aplicar descontos ou taxas adicionais baseado no tipo do quarto
        switch (tipo) {
            case STANDARD:
                return valorBase;
            case LUXO:
                return valorBase.multiply(BigDecimal.valueOf(1.2)); // 20% adicional
            case SUITE_PRESIDENCIAL:
                return valorBase.multiply(BigDecimal.valueOf(1.5)); // 50% adicional
            default:
                return valorBase;
        }
    }

    public TipoQuarto getTipo() {
        return tipo;
    }
} 