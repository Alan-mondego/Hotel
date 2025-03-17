package servicos;

import entidades.*;
import excecoes.*;
import repositorio.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class GerenciadorReservas {
    private final RepositorioReservas repositorioReservas;
    private final RepositorioHospedagens repositorioHospedagens;
    private final RepositorioClientes repositorioClientes;

    public GerenciadorReservas(RepositorioReservas repositorioReservas,
                              RepositorioHospedagens repositorioHospedagens,
                              RepositorioClientes repositorioClientes) {
        this.repositorioReservas = repositorioReservas;
        this.repositorioHospedagens = repositorioHospedagens;
        this.repositorioClientes = repositorioClientes;
    }

    public Reserva criarReserva(String cpfCliente, String idHospedagem,
                               LocalDateTime dataCheckIn, LocalDateTime dataCheckOut) {
        Cliente cliente = repositorioClientes.buscarPorCpf(cpfCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Hospedagem hospedagem = repositorioHospedagens.buscarPorId(idHospedagem);
        if (hospedagem == null) {
            throw new IllegalArgumentException("Hospedagem não encontrada");
        }

        if (!hospedagem.isDisponivel()) {
            throw new IllegalArgumentException("Hospedagem não está disponível");
        }

        validarDatas(dataCheckIn, dataCheckOut);

        Reserva reserva = new Reserva(cliente, hospedagem, dataCheckIn, dataCheckOut);
        hospedagem.setDisponivel(false);
        repositorioReservas.salvar(reserva);
        return reserva;
    }

    private void validarDatas(LocalDateTime dataCheckIn, LocalDateTime dataCheckOut) {
        LocalDateTime agora = LocalDateTime.now();

        if (dataCheckIn.isBefore(agora)) {
            throw new IllegalArgumentException("Data de check-in não pode ser no passado");
        }

        if (dataCheckIn.isAfter(dataCheckOut)) {
            throw new IllegalArgumentException("Data de check-in deve ser anterior à data de check-out");
        }

        if (dataCheckIn.toLocalTime().isBefore(LocalTime.of(14, 0))) {
            throw new IllegalArgumentException("Check-in só pode ser realizado a partir das 14:00");
        }

        if (dataCheckOut.toLocalTime().isAfter(LocalTime.of(12, 0))) {
            throw new IllegalArgumentException("Check-out deve ser realizado até as 12:00");
        }
    }

    public void cancelarReserva(String id) {
        Reserva reserva = buscarReserva(id);
        try {
            reserva.cancelar();
            repositorioReservas.salvar(reserva);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Não foi possível cancelar a reserva: " + e.getMessage());
        }
    }

    public void realizarCheckIn(String id) {
        Reserva reserva = buscarReserva(id);
        
        LocalDateTime agora = LocalDateTime.now();
        if (agora.toLocalTime().isBefore(LocalTime.of(14, 0))) {
            throw new IllegalArgumentException("Check-in só pode ser realizado a partir das 14:00");
        }

        try {
            reserva.realizarCheckIn();
            repositorioReservas.salvar(reserva);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Não foi possível realizar o check-in: " + e.getMessage());
        }
    }

    public void realizarCheckOut(String id) {
        Reserva reserva = buscarReserva(id);
        
        LocalDateTime agora = LocalDateTime.now();
        if (agora.toLocalTime().isAfter(LocalTime.of(12, 0))) {
            throw new IllegalArgumentException("Check-out deve ser realizado até as 12:00");
        }

        try {
            reserva.realizarCheckOut();
            repositorioReservas.salvar(reserva);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Não foi possível realizar o check-out: " + e.getMessage());
        }
    }

    public List<Reserva> listarReservasAtivas() {
        return repositorioReservas.buscarReservasAtivas();
    }

    public List<Reserva> listarReservasPendentes() {
        return repositorioReservas.buscarReservasPendentes();
    }

    public List<Reserva> listarReservasFinalizadas() {
        return repositorioReservas.buscarReservasFinalizadas();
    }

    public List<Reserva> listarReservasCanceladas() {
        return repositorioReservas.buscarReservasCanceladas();
    }

    public List<Reserva> buscarReservasPorCliente(String cpf) {
        return repositorioReservas.buscarReservasPorCliente(cpf);
    }

    public Reserva buscarPorId(String id) {
        return buscarReserva(id);
    }

    private Reserva buscarReserva(String id) {
        Reserva reserva = repositorioReservas.buscarPorId(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada");
        }
        return reserva;
    }
} 