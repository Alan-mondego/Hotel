package entidades;

public abstract class Hospedagem {
    private String identificacao;
    private int capacidadeMaxima;
    private double precoDiaria;
    private boolean disponivel;

    public Hospedagem(String identificacao, int capacidadeMaxima, double precoDiaria) {
        this.identificacao = identificacao;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoDiaria = precoDiaria;
        this.disponivel = true;
    }

    public abstract double calcularValorHospedagem(int numeroDiarias);

    // Getters e Setters
    public String getIdentificacao() {
        return identificacao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
} 