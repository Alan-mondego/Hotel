package excecoes;

public class ReservaNaoEncontradaException extends RuntimeException {
    public ReservaNaoEncontradaException() {
        super("Reserva não encontrada no sistema");
    }

    public ReservaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
} 