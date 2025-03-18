package entidades;

public class Apartamento extends Hospedagem {
    private int numeroQuartos;
    private boolean possuiCozinha;
    private int andar;

    public Apartamento(String identificacao, int capacidadeMaxima, double precoDiaria,
                      int numeroQuartos, boolean possuiCozinha, int andar) {
        super(identificacao, capacidadeMaxima, precoDiaria);
        this.numeroQuartos = numeroQuartos;
        this.possuiCozinha = possuiCozinha;
        this.andar = andar;
    }

    @Override
    public double calcularValorHospedagem(int numeroDiarias) {
        double valorBase = getPrecoDiaria() * numeroDiarias;
        
        // Adicional de 10% por quarto extra (além do primeiro)
        if (numeroQuartos > 1) {
            valorBase = valorBase * (1 + (0.1 * (numeroQuartos - 1)));
        }
        
        // Adicional de 15% para apartamentos com cozinha
        if (possuiCozinha) {
            valorBase = valorBase * 1.15;
        }
        
        // Adicional de 2% por andar (a partir do segundo andar)
        if (andar > 1) {
            valorBase = valorBase * (1 + (0.02 * (andar - 1)));
        }
        
        return valorBase;
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public boolean isPossuiCozinha() {
        return possuiCozinha;
    }

    public int getAndar() {
        return andar;
    }
} 