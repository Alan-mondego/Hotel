package repositorio;

import entidades.Reserva;
import entidades.Cliente;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepositorioReservas {
    private final Map<String, Reserva> reservas;
    private int proximoId;

    public RepositorioReservas() {
        this.reservas = new HashMap<>();
        this.proximoId = 1;
    }

    public void salvar(Reserva reserva) {
        if (reserva.getId() == null || reserva.getId().isEmpty()) {
            reserva.setId(String.valueOf(proximoId++));
        }
        reservas.put(reserva.getId(), reserva);
    }

    public Reserva buscarPorId(String id) {
        return reservas.get(id);
    }

    public List<Reserva> listarTodas() {
        return new ArrayList<>(reservas.values());
    }

    public List<Reserva> buscarReservasPorCliente(String cpf) {
        return reservas.values().stream()
                .filter(reserva -> reserva.getCliente().getCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarReservasAtivas() {
        return reservas.values().stream()
                .filter(Reserva::isAtiva)
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarReservasPendentes() {
        return reservas.values().stream()
                .filter(Reserva::isPendente)
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarReservasFinalizadas() {
        return reservas.values().stream()
                .filter(Reserva::isFinalizada)
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarReservasCanceladas() {
        return reservas.values().stream()
                .filter(Reserva::isCancelada)
                .collect(Collectors.toList());
    }

    public void remover(String id) {
        reservas.remove(id);
    }

    public boolean existe(String id) {
        return reservas.containsKey(id);
    }
} 