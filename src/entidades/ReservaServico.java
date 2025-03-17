package entidades;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ReservaServico {
    private String id;
    private Reserva reserva;
    private ServicoAdicional servico;
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    private StatusReservaServico status;

    public enum StatusReservaServico {
        AGENDADO,
        REALIZADO,
        CANCELADO
    }

    public ReservaServico(Reserva reserva, ServicoAdicional servico, LocalDateTime dataHora) {
        if (!reserva.isAtiva()) {
            throw new IllegalStateException("Apenas reservas ativas podem adicionar serviços");
        }
        this.reserva = reserva;
        this.servico = servico;
        this.dataHora = dataHora;
        this.status = StatusReservaServico.AGENDADO;
        this.valorTotal = servico.getPreco();
    }

    public void realizar() {
        if (status != StatusReservaServico.AGENDADO) {
            throw new IllegalStateException("Serviço não está agendado");
        }
        status = StatusReservaServico.REALIZADO;
    }

    public void cancelar() {
        if (status != StatusReservaServico.AGENDADO) {
            throw new IllegalStateException("Não é possível cancelar um serviço já realizado");
        }
        status = StatusReservaServico.CANCELADO;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public ServicoAdicional getServico() {
        return servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusReservaServico getStatus() {
        return status;
    }

    public boolean isAgendado() {
        return status == StatusReservaServico.AGENDADO;
    }

    public boolean isRealizado() {
        return status == StatusReservaServico.REALIZADO;
    }

    public boolean isCancelado() {
        return status == StatusReservaServico.CANCELADO;
    }
} 