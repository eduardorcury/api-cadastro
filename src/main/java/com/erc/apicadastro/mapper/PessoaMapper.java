package com.erc.apicadastro.mapper;

import com.erc.apicadastro.domain.Pessoa;
import com.erc.apicadastro.dto.PessoaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    Pessoa toDomain(PessoaDTO pessoaDTO);

    PessoaDTO toDTO(Pessoa pessoa);

}
