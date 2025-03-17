package entidades;

public class Funcionario {
    private String id;
    private String nome;
    private String cargo;
    private boolean ativo;

    public Funcionario(String id, String nome, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.ativo = true;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 