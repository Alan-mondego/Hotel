package repositorio;

import entidades.ReservaServico;
import entidades.Reserva;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepositorioReservasServico {
    private final Map<String, ReservaServico> reservasServico;
    private int proximoId;

    public RepositorioReservasServico() {
        this.reservasServico = new HashMap<>();
        this.proximoId = 1;
    }

    public void salvar(ReservaServico reservaServico) {
        if (reservaServico.getId() == null || reservaServico.getId().isEmpty()) {
            reservaServico.setId(String.valueOf(proximoId++));
        }
        reservasServico.put(reservaServico.getId(), reservaServico);
    }

    public ReservaServico buscarPorId(String id) {
        return reservasServico.get(id);
    }

    public List<ReservaServico> listarTodas() {
        return new ArrayList<>(reservasServico.values());
    }

    public List<ReservaServico> buscarPorReserva(Reserva reserva) {
        return reservasServico.values().stream()
                .filter(rs -> rs.getReserva().getId().equals(reserva.getId()))
                .collect(Collectors.toList());
    }

    public List<ReservaServico> buscarAgendados() {
        return reservasServico.values().stream()
                .filter(ReservaServico::isAgendado)
                .collect(Collectors.toList());
    }

    public List<ReservaServico> buscarRealizados() {
        return reservasServico.values().stream()
                .filter(ReservaServico::isRealizado)
                .collect(Collectors.toList());
    }

    public List<ReservaServico> buscarCancelados() {
        return reservasServico.values().stream()
                .filter(ReservaServico::isCancelado)
                .collect(Collectors.toList());
    }

    public void remover(String id) {
        reservasServico.remove(id);
    }

    public boolean existe(String id) {
        return reservasServico.containsKey(id);
    }
} 