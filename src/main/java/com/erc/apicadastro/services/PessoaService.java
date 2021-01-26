package com.erc.apicadastro.services;

import com.erc.apicadastro.domain.Pessoa;
import com.erc.apicadastro.dto.PessoaDTO;
import com.erc.apicadastro.exceptions.PessoaNaoEncontradaException;
import com.erc.apicadastro.mapper.PessoaMapper;
import com.erc.apicadastro.repositories.ContatoRepository;
import com.erc.apicadastro.repositories.PessoaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ContatoRepository contatoRepository;

    private final PessoaMapper mapper = PessoaMapper.INSTANCE;

    public PessoaService(PessoaRepository pessoaRepository, ContatoRepository contatoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.contatoRepository = contatoRepository;
    }

    public PessoaDTO encontrarPorId(Integer pessoaId) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);
        if (pessoaOptional.isPresent()) {
            return mapper.toDTO(pessoaOptional.get());
        } else {
            throw new PessoaNaoEncontradaException("Cliente não encontrado");
        }
    }

    public List<PessoaDTO> encontrarTodos() {
        List<PessoaDTO> pessoas = pessoaRepository.findAll()
                .stream().map(mapper::toDTO)
                .collect(Collectors.toList());
        return pessoas;
    }

    public Page<PessoaDTO> buscaPaginada(Integer numeroPagina,
                                         Integer quantidadePorPagina,
                                         String campoOrdenacao,
                                         String direcaoOrdenacao) {

        Pageable paginacao;

        if (direcaoOrdenacao.toUpperCase().equals("DESC")) {
            paginacao = PageRequest.of(numeroPagina, quantidadePorPagina, Sort.by(campoOrdenacao).descending());
        } else {
            paginacao = PageRequest.of(numeroPagina, quantidadePorPagina, Sort.by(campoOrdenacao).ascending());
        }

        Page<PessoaDTO> pagina = new PageImpl<>(pessoaRepository.findAll(paginacao)
                .stream().map(mapper::toDTO).collect(Collectors.toList()));

        return pagina;
    }

    public PessoaDTO salvar(PessoaDTO pessoa) {
        Pessoa pessoaSalva = pessoaRepository.save(mapper.toDomain(pessoa));
        contatoRepository.saveAll(pessoaSalva.getContatos());
        return mapper.toDTO(pessoaSalva);
    }

    public PessoaDTO atualizar(Integer pessoaId, PessoaDTO pessoaAtualizada) {

        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = mapper.toDomain(pessoaAtualizada);
            pessoa.setId(pessoaId);
            return mapper.toDTO(pessoaRepository.save(pessoa));
        } else {
            throw new PessoaNaoEncontradaException("Cliente não encontrado");
        }
    }

    public void deletar(Integer id) {
        pessoaRepository.deleteById(id);
    }

}
