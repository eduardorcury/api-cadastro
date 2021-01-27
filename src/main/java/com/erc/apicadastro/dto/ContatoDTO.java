package com.erc.apicadastro.dto;

import com.erc.apicadastro.domain.Pessoa;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class ContatoDTO {

    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "Telefone não pode estar em branco")
    @Size(min = 4, max = 30, message = "Número de telefone inválido")
    private String telefone;

    @NotBlank(message = "E-mail não pode estar em branco")
    @Email(message = "Endereço de e-mail inválido")
    private String email;

    public ContatoDTO(@NotBlank(message = "Nome não pode estar em branco") String nome,
                      @NotBlank(message = "Telefone não pode estar em branco") @Size(min = 4, max = 20, message = "Número de telefone inválido") String telefone,
                      @NotBlank(message = "E-mail não pode estar em branco") @Email(message = "Endereço de e-mail inválido") String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContatoDTO that = (ContatoDTO) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, email);
    }
}
