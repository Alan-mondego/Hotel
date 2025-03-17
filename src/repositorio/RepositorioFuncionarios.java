package repositorio;

import entidades.Funcionario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class RepositorioFuncionarios {
    private final Map<String, Funcionario> funcionarios;

    public RepositorioFuncionarios() {
        this.funcionarios = new HashMap<>();
    }

    public void salvar(Funcionario funcionario) {
        funcionarios.put(funcionario.getId(), funcionario);
    }

    public Funcionario buscarPorId(String id) {
        return funcionarios.get(id);
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios.values());
    }

    public void remover(String id) {
        funcionarios.remove(id);
    }

    public boolean existe(String id) {
        return funcionarios.containsKey(id);
    }

    public boolean existeAlgumFuncionario() {
        return !funcionarios.isEmpty();
    }
} 