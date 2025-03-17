package servicos;

import entidades.*;
import repositorio.RepositorioHospedagens;
import java.math.BigDecimal;
import java.util.List;

public class GerenciadorHospedagens {
    private final RepositorioHospedagens repositorio;

    public GerenciadorHospedagens(RepositorioHospedagens repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarQuarto(String identificacao, int capacidadeMaxima, 
                              BigDecimal precoDiaria, Quarto.TipoQuarto tipo) {
        if (repositorio.existe(identificacao)) {
            throw new IllegalArgumentException("Identificação já cadastrada");
        }

        Quarto quarto = new Quarto(identificacao, capacidadeMaxima, precoDiaria, tipo);
        repositorio.salvar(quarto);
    }

    public void cadastrarCabana(String identificacao, int capacidadeMaxima,
                               BigDecimal precoDiaria, boolean possuiLareira, 
                               boolean possuiVistaMar) {
        if (repositorio.existe(identificacao)) {
            throw new IllegalArgumentException("Identificação já cadastrada");
        }

        Cabana cabana = new Cabana(identificacao, capacidadeMaxima, precoDiaria, 
                                  possuiLareira, possuiVistaMar);
        repositorio.salvar(cabana);
    }

    public void cadastrarApartamento(String identificacao, int capacidadeMaxima,
                                   BigDecimal precoDiaria, int numeroQuartos,
                                   boolean possuiCozinha, int andar) {
        if (repositorio.existe(identificacao)) {
            throw new IllegalArgumentException("Identificação já cadastrada");
        }

        Apartamento apartamento = new Apartamento(identificacao, capacidadeMaxima,
                                                precoDiaria, numeroQuartos,
                                                possuiCozinha, andar);
        repositorio.salvar(apartamento);
    }

    public Hospedagem buscarPorId(String identificacao) {
        Hospedagem hospedagem = repositorio.buscarPorId(identificacao);
        if (hospedagem == null) {
            throw new IllegalArgumentException("Hospedagem não encontrada");
        }
        return hospedagem;
    }

    public List<Hospedagem> listarTodas() {
        return repositorio.listarTodas();
    }

    public List<Hospedagem> listarDisponiveis() {
        return repositorio.listarDisponiveis();
    }

    public void atualizarDisponibilidade(String identificacao, boolean disponivel) {
        if (!repositorio.existe(identificacao)) {
            throw new IllegalArgumentException("Hospedagem não encontrada");
        }
        repositorio.atualizarDisponibilidade(identificacao, disponivel);
    }

    public void atualizarPrecoDiaria(String identificacao, BigDecimal novoPrecoDiaria) {
        Hospedagem hospedagem = buscarPorId(identificacao);
        hospedagem.setPrecoDiaria(novoPrecoDiaria);
        repositorio.atualizar(hospedagem);
    }

    public void removerHospedagem(String identificacao) {
        if (!repositorio.existe(identificacao)) {
            throw new IllegalArgumentException("Hospedagem não encontrada");
        }
        repositorio.remover(identificacao);
    }
} 