package entidades;

public class Cabana extends Hospedagem {
    private boolean possuiLareira;
    private boolean possuiVistaMar;

    public Cabana(String identificacao, int capacidadeMaxima, double precoDiaria,
                  boolean possuiLareira, boolean possuiVistaMar) {
        super(identificacao, capacidadeMaxima, precoDiaria);
        this.possuiLareira = possuiLareira;
        this.possuiVistaMar = possuiVistaMar;
    }

    @Override
    public double calcularValorHospedagem(int numeroDiarias) {
        double valorBase = getPrecoDiaria() * numeroDiarias;
        
        // Adicional de 15% para cabanas com lareira
        if (possuiLareira) {
            valorBase = valorBase * 1.15;
        }
        
        // Adicional de 25% para cabanas com vista para o mar
        if (possuiVistaMar) {
            valorBase = valorBase * 1.25;
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