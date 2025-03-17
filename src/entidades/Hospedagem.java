package entidades;

import java.math.BigDecimal;

public abstract class Hospedagem {
    private String identificacao;
    private int capacidadeMaxima;
    private BigDecimal precoDiaria;
    private boolean disponivel;

    public Hospedagem(String identificacao, int capacidadeMaxima, BigDecimal precoDiaria) {
        this.identificacao = identificacao;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoDiaria = precoDiaria;
        this.disponivel = true;
    }

    public abstract BigDecimal calcularValorHospedagem(int numeroDiarias);

    // Getters e Setters
    public String getIdentificacao() {
        return identificacao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public BigDecimal getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(BigDecimal precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
} 