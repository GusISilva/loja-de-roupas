package model;

import java.math.BigDecimal;

public class Product {

    private int id;
    private String nome;
    private String descricao;
    private int estoque;
    private BigDecimal valor;
    private String observacoes;

    public Product(String nome, String descricao, int estoque, BigDecimal valor, String observacoes) {
        this.nome = nome;
        this.descricao = descricao;
        this.estoque = estoque;
        this.valor = valor;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}