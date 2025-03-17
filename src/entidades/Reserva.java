package entidades;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Reserva {
    private String id;
    private Cliente cliente;
    private Hospedagem hospedagem;
    private LocalDateTime dataCheckIn;
    private LocalDateTime dataCheckOut;
    private StatusReserva status;
    private BigDecimal valorTotal;

    public enum StatusReserva {
        PENDENTE,      // Reserva criada, aguardando check-in
        ATIVA,         // Check-in realizado
        FINALIZADA,    // Check-out realizado
        CANCELADA      // Reserva cancelada
    }

    public Reserva(Cliente cliente, Hospedagem hospedagem, LocalDateTime dataCheckIn, LocalDateTime dataCheckOut) {
        this.cliente = cliente;
        this.hospedagem = hospedagem;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.status = StatusReserva.PENDENTE;
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        long dias = java.time.Duration.between(dataCheckIn, dataCheckOut).toDays();
        if (dias < 1) dias = 1; // Mínimo de 1 dia
        this.valorTotal = hospedagem.calcularValorHospedagem((int) dias);
    }

    public void cancelar() {
        if (status != StatusReserva.FINALIZADA && status != StatusReserva.CANCELADA) {
            this.status = StatusReserva.CANCELADA;
            this.hospedagem.setDisponivel(true);
        } else {
            throw new IllegalStateException("Não é possível cancelar esta reserva");
        }
    }

    public void realizarCheckIn() {
        if (status == StatusReserva.PENDENTE) {
            this.status = StatusReserva.ATIVA;
        } else {
            throw new IllegalStateException("Check-in só pode ser realizado em reservas pendentes");
        }
    }

    public void realizarCheckOut() {
        if (status == StatusReserva.ATIVA) {
            this.status = StatusReserva.FINALIZADA;
            this.hospedagem.setDisponivel(true);
        } else {
            throw new IllegalStateException("Check-out só pode ser realizado em reservas ativas");
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Hospedagem getHospedagem() {
        return hospedagem;
    }

    public LocalDateTime getDataCheckIn() {
        return dataCheckIn;
    }

    public LocalDateTime getDataCheckOut() {
        return dataCheckOut;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    // Métodos de verificação de status
    public boolean isAtiva() {
        return status == StatusReserva.ATIVA;
    }

    public boolean isPendente() {
        return status == StatusReserva.PENDENTE;
    }

    public boolean isFinalizada() {
        return status == StatusReserva.FINALIZADA;
    }

    public boolean isCancelada() {
        return status == StatusReserva.CANCELADA;
    }
} 