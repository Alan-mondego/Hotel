package repositorio;

import entidades.ServicoAdicional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class RepositorioServicosAdicionais {
    private final Map<String, ServicoAdicional> servicos;
    private int proximoId;

    public RepositorioServicosAdicionais() {
        this.servicos = new HashMap<>();
        this.proximoId = 1;
    }

    public void salvar(ServicoAdicional servico) {
        if (servico.getId() == null || servico.getId().isEmpty()) {
            servico.setId(String.valueOf(proximoId++));
        }
        servicos.put(servico.getId(), servico);
    }

    public ServicoAdicional buscarPorId(String id) {
        return servicos.get(id);
    }

    public List<ServicoAdicional> listarTodos() {
        return new ArrayList<>(servicos.values());
    }

    public List<ServicoAdicional> listarDisponiveis() {
        return servicos.values().stream()
                .filter(ServicoAdicional::isDisponivel)
                .toList();
    }

    public List<ServicoAdicional> buscarPorTipo(ServicoAdicional.TipoServico tipo) {
        return servicos.values().stream()
                .filter(s -> s.getTipo() == tipo && s.isDisponivel())
                .toList();
    }

    public void remover(String id) {
        servicos.remove(id);
    }

    public boolean existe(String id) {
        return servicos.containsKey(id);
    }
} 