package servicos;

import entidades.Cliente;
import repositorio.RepositorioClientes;
import java.util.List;

public class GerenciadorClientes {
    private final RepositorioClientes repositorio;

    public GerenciadorClientes(RepositorioClientes repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarCliente(String nome, String cpf, String telefone) {
        if (repositorio.existe(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        validarCPF(cpf);
        validarTelefone(telefone);

        Cliente cliente = new Cliente(nome, cpf, telefone);
        repositorio.salvar(cliente);
    }

    public Cliente buscarPorCpf(String cpf) {
        Cliente cliente = repositorio.buscarPorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        return cliente;
    }

    public List<Cliente> listarTodos() {
        return repositorio.listarTodos();
    }

    public void atualizarCliente(Cliente cliente) {
        if (!repositorio.existe(cliente.getCpf())) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        repositorio.atualizar(cliente);
    }

    public void removerCliente(String cpf) {
        if (!repositorio.existe(cpf)) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        repositorio.remover(cpf);
    }

    private void validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        // Remove caracteres especiais
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
        }
        
        // Aqui você pode adicionar mais validações do CPF se necessário
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }
        
        // Remove caracteres especiais
        telefone = telefone.replaceAll("[^0-9]", "");
        
        if (telefone.length() < 10 || telefone.length() > 11) {
            throw new IllegalArgumentException("Telefone deve conter entre 10 e 11 dígitos");
        }
    }
} 