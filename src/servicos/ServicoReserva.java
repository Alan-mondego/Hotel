package servicos;

import entidades.*;
import excecoes.HotelException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicoReserva {
    private final Map<String, Reserva> reservas;
    private final Map<String, Cliente> clientes;
    private final Map<String, Hospedagem> hospedagens;
    private final Map<String, ServicoAdicional> servicosAdicionais;
    private int proximoId;

    public ServicoReserva() {
        this.reservas = new HashMap<>();
        this.clientes = new HashMap<>();
        this.hospedagens = new HashMap<>();
        this.servicosAdicionais = new HashMap<>();
        this.proximoId = 1;
    }

    public Reserva criarReserva(Cliente cliente, Hospedagem hospedagem, 
                               LocalDateTime dataCheckIn, LocalDateTime dataCheckOut) {
        validarHorarioCheckIn(dataCheckIn);
        validarHorarioCheckOut(dataCheckOut);
        validarDisponibilidadeHospedagem(hospedagem, dataCheckIn, dataCheckOut);

        // Criar reserva com ID sequencial
        Reserva reserva = new Reserva(cliente, hospedagem, dataCheckIn, dataCheckOut);
        String id = String.valueOf(proximoId++);
        reserva.setId(id);
        
        hospedagem.setDisponivel(false);
        reservas.put(id, reserva);
        
        return reserva;
    }

    public void realizarCheckIn(String reservaId) {
        Reserva reserva = buscarReserva(reservaId);
        if (reserva == null) {
            throw new HotelException(HotelException.TipoExcecao.RESERVA_NAO_ENCONTRADA);
        }

        LocalDateTime agora = LocalDateTime.now();
        if (agora.toLocalTime().isBefore(LocalTime.of(14, 0))) {
            throw new HotelException(HotelException.TipoExcecao.CHECKIN_FORA_HORARIO);
        }

        try {
            reserva.realizarCheckIn();
        } catch (IllegalStateException e) {
            throw new HotelException(HotelException.TipoExcecao.OPERACAO_INVALIDA, e.getMessage());
        }
    }

    public void realizarCheckOut(String reservaId) {
        Reserva reserva = buscarReserva(reservaId);
        if (reserva == null) {
            throw new HotelException(HotelException.TipoExcecao.RESERVA_NAO_ENCONTRADA);
        }

        LocalDateTime agora = LocalDateTime.now();
        if (agora.toLocalTime().isAfter(LocalTime.of(12, 0))) {
            throw new HotelException(HotelException.TipoExcecao.CHECKOUT_FORA_HORARIO);
        }

        try {
            reserva.realizarCheckOut();
        } catch (IllegalStateException e) {
            throw new HotelException(HotelException.TipoExcecao.OPERACAO_INVALIDA, e.getMessage());
        }
    }

    public void cancelarReserva(String reservaId) {
        Reserva reserva = buscarReserva(reservaId);
        if (reserva == null) {
            throw new HotelException(HotelException.TipoExcecao.RESERVA_NAO_ENCONTRADA);
        }

        try {
            reserva.cancelar();
        } catch (IllegalStateException e) {
            throw new HotelException(HotelException.TipoExcecao.OPERACAO_INVALIDA, e.getMessage());
        }
    }

    public List<Reserva> listarReservasAtivas() {
        return reservas.values().stream()
            .filter(Reserva::isAtiva)
            .collect(Collectors.toList());
    }

    public Reserva buscarReserva(String id) {
        return reservas.get(id);
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas.values());
    }

    private void validarHorarioCheckIn(LocalDateTime dataCheckIn) {
        if (dataCheckIn.toLocalTime().isBefore(LocalTime.of(14, 0))) {
            throw new HotelException(HotelException.TipoExcecao.CHECKIN_FORA_HORARIO);
        }
    }

    private void validarHorarioCheckOut(LocalDateTime dataCheckOut) {
        if (dataCheckOut.toLocalTime().isAfter(LocalTime.of(12, 0))) {
            throw new HotelException(HotelException.TipoExcecao.CHECKOUT_FORA_HORARIO);
        }
    }

    private void validarDisponibilidadeHospedagem(Hospedagem hospedagem, 
                                                 LocalDateTime dataCheckIn,
                                                 LocalDateTime dataCheckOut) {
        if (!hospedagem.isDisponivel()) {
            throw new HotelException(HotelException.TipoExcecao.HOSPEDAGEM_INDISPONIVEL);
        }
    }

    // Métodos para gerenciar clientes, hospedagens e serviços adicionais
    public void cadastrarCliente(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    public void cadastrarHospedagem(Hospedagem hospedagem) {
        hospedagens.put(hospedagem.getIdentificacao(), hospedagem);
    }

    public void cadastrarServicoAdicional(ServicoAdicional servico) {
        servicosAdicionais.put(servico.getId(), servico);
    }

    public List<Hospedagem> listarHospedagensDisponiveis() {
        return hospedagens.values().stream()
            .filter(Hospedagem::isDisponivel)
            .collect(Collectors.toList());
    }
} 