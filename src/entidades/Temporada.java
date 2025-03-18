package entidades;

public enum Temporada {
    BAIXA(0.8),  // 20% de desconto
    MEDIA(1.0),  // Preço normal
    ALTA(1.5);   // 50% de aumento

    private final double multiplicador;

    Temporada(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }
    public static Temporada obterTemporadaPorMes(int mes) {
        if (mes== 1 || mes== 2 || mes == 7 || mes == 12) {
            return ALTA;
        }
        if (mes == 3  || mes == 6 || mes==8) {
            return MEDIA;
        }
        return BAIXA;
    }
}

