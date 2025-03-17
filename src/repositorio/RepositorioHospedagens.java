package repositorio;

import entidades.Hospedagem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepositorioHospedagens {
    private final Map<String, Hospedagem> hospedagens;

    public RepositorioHospedagens() {
        this.hospedagens = new HashMap<>();
    }

    public void salvar(Hospedagem hospedagem) {
        hospedagens.put(hospedagem.getIdentificacao(), hospedagem);
    }

    public Hospedagem buscarPorId(String identificacao) {
        return hospedagens.get(identificacao);
    }

    public List<Hospedagem> listarTodas() {
        return new ArrayList<>(hospedagens.values());
    }

    public List<Hospedagem> listarDisponiveis() {
        return hospedagens.values().stream()
            .filter(Hospedagem::isDisponivel)
            .collect(Collectors.toList());
    }

    public void remover(String identificacao) {
        hospedagens.remove(identificacao);
    }

    public boolean existe(String identificacao) {
        return hospedagens.containsKey(identificacao);
    }

    public void atualizar(Hospedagem hospedagem) {
        if (existe(hospedagem.getIdentificacao())) {
            hospedagens.put(hospedagem.getIdentificacao(), hospedagem);
        } else {
            throw new IllegalArgumentException("Hospedagem não encontrada");
        }
    }

    public void atualizarDisponibilidade(String identificacao, boolean disponivel) {
        Hospedagem hospedagem = buscarPorId(identificacao);
        if (hospedagem != null) {
            hospedagem.setDisponivel(disponivel);
        } else {
            throw new IllegalArgumentException("Hospedagem não encontrada");
        }
    }
} 