package com.erc.apicadastro.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Contato> contatos = new ArrayList<>();

    public Pessoa() {

    }

    public Pessoa(String nome, String cpf, Date nascimento, List<Contato> contatos) {
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.contatos = contatos;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }
}
