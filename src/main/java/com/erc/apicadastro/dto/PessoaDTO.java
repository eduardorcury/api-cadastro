package com.erc.apicadastro.dto;

import com.erc.apicadastro.domain.Contato;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PessoaDTO {

    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    // TODO checar nulo
    @CPF(message = "Número de CPF inválido")
    private String cpf;

    @NotNull(message = "Por favor, insira uma data de nascimento")
    @Past(message = "Data de nascimento inválida")
    private Date nascimento;

    private List<Contato> contatos = new ArrayList<>();

}
