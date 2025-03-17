package excecoes;

public class ServicoNaoPermitidoException extends RuntimeException {
    public ServicoNaoPermitidoException() {
        super("Serviço adicional não permitido sem hospedagem ativa");
    }

    public ServicoNaoPermitidoException(String mensagem) {
        super(mensagem);
    }
} 