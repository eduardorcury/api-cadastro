package com.erc.apicadastro.domain;

import javax.persistence.*;

@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Pessoa pessoa;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    public Contato() {

    }

    public Contato(Pessoa pessoa, String nome, String telefone, String email) {
        this.pessoa = pessoa;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
}
