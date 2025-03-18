package servicos;

import entidades.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServicoRelatorio {
    private final ServicoReserva servicoReserva;

    public ServicoRelatorio(ServicoReserva servicoReserva) {
        this.servicoReserva = servicoReserva;
    }

    public String gerarRelatorioReservasAtivas() {
        List<Reserva> reservasAtivas = servicoReserva.listarReservasAtivas();
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== Relatório de Reservas Ativas ===\n\n");

        for (Reserva reserva : reservasAtivas) {
            relatorio.append(String.format("Reserva ID: %s\n", reserva.getId()));
            relatorio.append(String.format("Cliente: %s\n", reserva.getCliente().getNome()));
            relatorio.append(String.format("Hospedagem: %s\n", reserva.getHospedagem().getIdentificacao()));
            relatorio.append(String.format("Check-in: %s\n", reserva.getDataCheckIn()));
            relatorio.append(String.format("Check-out: %s\n", reserva.getDataCheckOut()));
            relatorio.append(String.format("Valor Total: R$ %.2f\n", reserva.getValorTotal()));
            relatorio.append("----------------------------\n");
        }

        return relatorio.toString();
    }

    public String gerarRelatorioOcupacao() {
        List<Hospedagem> hospedagensDisponiveis = servicoReserva.listarHospedagensDisponiveis();
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== Relatório de Ocupação ===\n\n");

        long totalHospedagens = hospedagensDisponiveis.size();
        long hospedagensOcupadas = hospedagensDisponiveis.stream()
            .filter(h -> !h.isDisponivel())
            .count();

        relatorio.append(String.format("Total de Unidades: %d\n", totalHospedagens));
        relatorio.append(String.format("Unidades Ocupadas: %d\n", hospedagensOcupadas));
        relatorio.append(String.format("Unidades Disponíveis: %d\n", 
            totalHospedagens - hospedagensOcupadas));
        relatorio.append(String.format("Taxa de Ocupação: %.2f%%\n", 
            (hospedagensOcupadas * 100.0) / totalHospedagens));

        return relatorio.toString();
    }

    public String gerarRelatorioFinanceiro(LocalDateTime inicio, LocalDateTime fim) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== Relatório Financeiro ===\n\n");
        relatorio.append(String.format("Período: %s a %s\n\n", inicio, fim));

        double totalReceita = 0.0;
        int totalReservas = 0;

        List<Reserva> reservasPeriodo = servicoReserva.listarReservasAtivas().stream()
            .filter(r -> !r.getDataCheckIn().isBefore(inicio) && !r.getDataCheckOut().isAfter(fim))
            .collect(Collectors.toList());

        for (Reserva reserva : reservasPeriodo) {
            totalReceita += reserva.getValorTotal();
            totalReservas++;
        }

        relatorio.append(String.format("Total de Reservas: %d\n", totalReservas));
        relatorio.append(String.format("Receita Total: R$ %.2f\n", totalReceita));
        relatorio.append(String.format("Média por Reserva: R$ %.2f\n", 
            totalReservas > 0 ? totalReceita / totalReservas : 0.0));

        return relatorio.toString();
    }

    public String gerarHistoricoCliente(String cpf) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== Histórico do Cliente ===\n\n");

        List<Reserva> reservasCliente = servicoReserva.listarReservasAtivas().stream()
            .filter(r -> r.getCliente().getCpf().equals(cpf))
            .collect(Collectors.toList());

        if (reservasCliente.isEmpty()) {
            relatorio.append("Nenhuma reserva encontrada para este cliente.\n");
            return relatorio.toString();
        }

        Cliente cliente = reservasCliente.get(0).getCliente();
        relatorio.append(String.format("Cliente: %s\n", cliente.getNome()));
        relatorio.append(String.format("CPF: %s\n", cliente.getCpf()));
        relatorio.append(String.format("Telefone: %s\n\n", cliente.getTelefone()));
        relatorio.append("Histórico de Reservas:\n");

        for (Reserva reserva : reservasCliente) {
            relatorio.append(String.format("\nReserva ID: %s\n", reserva.getId()));
            relatorio.append(String.format("Status: %s\n", reserva.getStatus()));
            relatorio.append(String.format("Check-in: %s\n", reserva.getDataCheckIn()));
            relatorio.append(String.format("Check-out: %s\n", reserva.getDataCheckOut()));
            relatorio.append(String.format("Valor: R$ %.2f\n", reserva.getValorTotal()));
            relatorio.append("----------------------------\n");
        }

        return relatorio.toString();
    }
} 