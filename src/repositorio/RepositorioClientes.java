package repositorio;

import entidades.Cliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class RepositorioClientes {
    private final Map<String, Cliente> clientes;

    public RepositorioClientes() {
        this.clientes = new HashMap<>();
    }

    public void salvar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    public void remover(String cpf) {
        clientes.remove(cpf);
    }

    public boolean existe(String cpf) {
        return clientes.containsKey(cpf);
    }

    public void atualizar(Cliente cliente) {
        if (existe(cliente.getCpf())) {
            clientes.put(cliente.getCpf(), cliente);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }
} 