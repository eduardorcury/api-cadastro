package com.erc.apicadastro.mapper;

import com.erc.apicadastro.domain.Contato;
import com.erc.apicadastro.domain.Pessoa;
import com.erc.apicadastro.dto.ContatoDTO;
import com.erc.apicadastro.dto.PessoaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    Pessoa toDomain(PessoaDTO pessoaDTO);

    PessoaDTO toDTO(Pessoa pessoa);

    Contato toDomain(ContatoDTO contatoDTO);

    @Mapping(target = "nome", source = "contato.nome")
    @Mapping(target = "telefone", source = "contato.telefone")
    @Mapping(target = "email", source = "contato.email")
    ContatoDTO toDTO(Contato contato);


}
