package com.erc.apicadastro.dto;

import com.erc.apicadastro.domain.Contato;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PessoaDTO {

    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @NotNull(message = "Por favor, insira um número de CPF")
    @CPF(message = "Número de CPF inválido")
    private String cpf;

    @NotNull(message = "Por favor, insira uma data de nascimento")
    @Past(message = "Data de nascimento inválida")
    private Date nascimento;

    @Valid
    @NotEmpty(message = "Por favor, insira pelo menos um contato")
    private List<ContatoDTO> contatos;

    public PessoaDTO(@NotBlank(message = "Nome não pode estar em branco") String nome,
                     @CPF(message = "Número de CPF inválido") String cpf,
                     @NotNull(message = "Por favor, insira uma data de nascimento")
                     @Past(message = "Data de nascimento inválida") Date nascimento,
                     @Valid @NotEmpty(message = "Por favor, insira pelo menos um contato") List<ContatoDTO> contatos) {
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.contatos = contatos;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public List<ContatoDTO> getContatos() {
        return contatos;
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

    public void setContatos(List<ContatoDTO> contatos) {
        this.contatos = contatos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaDTO pessoaDTO = (PessoaDTO) o;
        return Objects.equals(nome, pessoaDTO.nome) &&
                Objects.equals(cpf, pessoaDTO.cpf) &&
                Objects.equals(nascimento, pessoaDTO.nascimento) &&
                Objects.equals(contatos, pessoaDTO.contatos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cpf, nascimento, contatos);
    }
}
