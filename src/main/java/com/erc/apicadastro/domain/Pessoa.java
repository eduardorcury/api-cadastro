package com.erc.apicadastro.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contato> contatos = new ArrayList<>();

    public Pessoa() {

    }

    public Pessoa(String nome, String cpf, Date nascimento, List<Contato> contatos) {
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.contatos = contatos;
    }

}
