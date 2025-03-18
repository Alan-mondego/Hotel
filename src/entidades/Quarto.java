package entidades;

public class Quarto extends Hospedagem {
    public Quarto(String identificacao, int capacidadeMaxima, double precoDiaria) {
        super(identificacao, capacidadeMaxima, precoDiaria);
    }

    @Override
    public double calcularValorHospedagem(int numeroDiarias) {
        return getPrecoDiaria() * numeroDiarias;
    }
} 