package br.edu.ifspsaocarlos.produtocp.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String descricao;
    private String preco;

    public Produto(){}

    public Produto(long id, String descricao, String preco) {
        super();
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}