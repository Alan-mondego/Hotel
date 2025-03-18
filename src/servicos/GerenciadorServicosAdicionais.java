package servicos;

import entidades.ServicoAdicional;
import entidades.ReservaServico;
import entidades.Reserva;
import repositorio.RepositorioServicosAdicionais;
import repositorio.RepositorioReservasServico;
import java.time.LocalDateTime;
import java.util.List;

public class GerenciadorServicosAdicionais {
    private final RepositorioServicosAdicionais repositorioServicosAdicionais;
    private final RepositorioReservasServico repositorioReservasServico;

    public GerenciadorServicosAdicionais(
            RepositorioServicosAdicionais repositorioServicosAdicionais,
            RepositorioReservasServico repositorioReservasServico) {
        this.repositorioServicosAdicionais = repositorioServicosAdicionais;
        this.repositorioReservasServico = repositorioReservasServico;
    }

    public void cadastrarServico(String nome, String descricao, double preco, ServicoAdicional.TipoServico tipo) {
        ServicoAdicional servico = new ServicoAdicional(nome, descricao, preco, tipo);
        repositorioServicosAdicionais.salvar(servico);
    }

    public List<ServicoAdicional> listarServicosDisponiveis() {
        return repositorioServicosAdicionais.listarDisponiveis();
    }

    public List<ServicoAdicional> listarServicosPorTipo(ServicoAdicional.TipoServico tipo) {
        return repositorioServicosAdicionais.buscarPorTipo(tipo);
    }

    public ReservaServico reservarServico(Reserva reserva, String idServico, LocalDateTime dataHora) {
        ServicoAdicional servico = repositorioServicosAdicionais.buscarPorId(idServico);
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado");
        }
        if (!servico.isDisponivel()) {
            throw new IllegalArgumentException("Serviço não está disponível");
        }

        ReservaServico reservaServico = new ReservaServico(reserva, servico, dataHora);
        repositorioReservasServico.salvar(reservaServico);
        return reservaServico;
    }

    public void realizarServico(String idReservaServico) {
        ReservaServico reservaServico = repositorioReservasServico.buscarPorId(idReservaServico);
        if (reservaServico == null) {
            throw new IllegalArgumentException("Reserva de serviço não encontrada");
        }
        reservaServico.realizar();
        repositorioReservasServico.salvar(reservaServico);
    }

    public void cancelarServico(String idReservaServico) {
        ReservaServico reservaServico = repositorioReservasServico.buscarPorId(idReservaServico);
        if (reservaServico == null) {
            throw new IllegalArgumentException("Reserva de serviço não encontrada");
        }
        reservaServico.cancelar();
        repositorioReservasServico.salvar(reservaServico);
    }

    public List<ReservaServico> listarServicosAgendados() {
        return repositorioReservasServico.buscarAgendados();
    }

    public List<ReservaServico> listarServicosRealizados() {
        return repositorioReservasServico.buscarRealizados();
    }

    public List<ReservaServico> listarServicosCancelados() {
        return repositorioReservasServico.buscarCancelados();
    }

    public List<ReservaServico> listarServicosPorReserva(Reserva reserva) {
        return repositorioReservasServico.buscarPorReserva(reserva);
    }
} 