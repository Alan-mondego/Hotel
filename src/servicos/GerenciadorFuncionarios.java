package servicos;

import entidades.Funcionario;
import repositorio.RepositorioFuncionarios;
import java.util.List;
import java.util.UUID;

public class GerenciadorFuncionarios {
    private final RepositorioFuncionarios repositorio;

    public GerenciadorFuncionarios(RepositorioFuncionarios repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarFuncionario(String nome, String cargo) {
        String id = UUID.randomUUID().toString();
        Funcionario funcionario = new Funcionario(id, nome, cargo);
        repositorio.salvar(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return repositorio.listarTodos();
    }

    public boolean existeAlgumFuncionario() {
        return repositorio.existeAlgumFuncionario();
    }

    public void desativarFuncionario(String id) {
        Funcionario funcionario = repositorio.buscarPorId(id);
        if (funcionario != null) {
            funcionario.setAtivo(false);
            repositorio.salvar(funcionario);
        } else {
            throw new IllegalArgumentException("Funcionário não encontrado");
        }
    }
} 