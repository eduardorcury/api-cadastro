package com.erc.apicadastro.dto;

import com.erc.apicadastro.domain.Pessoa;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContatoDTO {

    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "Telefone não pode estar em branco")
    @Size(min = 4, max = 20, message = "Número de telefone inválido")
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
}
