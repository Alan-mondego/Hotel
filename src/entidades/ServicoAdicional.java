package entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServicoAdicional {
    private String id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private TipoServico tipo;
    private boolean disponivel;

    public enum TipoServico {
        PASSEIO_TURISTICO,
        TRANSFER_AEROPORTO,
        TRANSFER_RODOVIARIA,
        LAVANDERIA
    }

    public ServicoAdicional(String nome, String descricao, BigDecimal preco, TipoServico tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tipo = tipo;
        this.disponivel = true;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicoAdicional servico = (ServicoAdicional) o;
        return id.equals(servico.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 