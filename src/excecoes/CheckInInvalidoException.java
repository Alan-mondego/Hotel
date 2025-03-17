package excecoes;

public class CheckInInvalidoException extends RuntimeException {
    public CheckInInvalidoException() {
        super("Check-in só é permitido a partir das 14h");
    }

    public CheckInInvalidoException(String mensagem) {
        super(mensagem);
    }

    public static class CheckOutInvalidoException extends RuntimeException {
        public CheckOutInvalidoException() {
            super("Check-out deve ser realizado até as 12h");
        }

        public CheckOutInvalidoException(String mensagem) {
            super(mensagem);
        }
    }
} 