package excecoes;

public class HospedagemIndisponivelException extends RuntimeException {
    public HospedagemIndisponivelException() {
        super("Hospedagem não está disponível para o período solicitado");
    }

    public HospedagemIndisponivelException(String mensagem) {
        super(mensagem);
    }
} 