package excecoes;

public class HotelException extends RuntimeException {
    private final TipoExcecao tipo;

    public enum TipoExcecao {
        RESERVA_NAO_ENCONTRADA,
        CHECKIN_FORA_HORARIO,
        CHECKOUT_FORA_HORARIO,
        HOSPEDAGEM_INDISPONIVEL,
        OPERACAO_INVALIDA,
        CLIENTE_NAO_ENCONTRADO,
        HOSPEDAGEM_NAO_ENCONTRADA
    }

    public HotelException(TipoExcecao tipo) {
        super(obterMensagem(tipo));
        this.tipo = tipo;
    }

    public HotelException(TipoExcecao tipo, String mensagem) {
        super(mensagem);
        this.tipo = tipo;
    }

    public TipoExcecao getTipo() {
        return tipo;
    }

    private static String obterMensagem(TipoExcecao tipo) {
        switch (tipo) {
            case RESERVA_NAO_ENCONTRADA:
                return "Reserva não encontrada";
            case CHECKIN_FORA_HORARIO:
                return "Check-in só pode ser realizado a partir das 14:00";
            case CHECKOUT_FORA_HORARIO:
                return "Check-out deve ser realizado até as 12:00";
            case HOSPEDAGEM_INDISPONIVEL:
                return "Hospedagem não está disponível para o período solicitado";
            case OPERACAO_INVALIDA:
                return "Operação inválida para o estado atual da reserva";
            case CLIENTE_NAO_ENCONTRADO:
                return "Cliente não encontrado";
            case HOSPEDAGEM_NAO_ENCONTRADA:
                return "Hospedagem não encontrada";
            default:
                return "Erro desconhecido";
        }
    }
} 