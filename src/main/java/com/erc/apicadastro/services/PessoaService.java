package com.erc.apicadastro.services;

import com.erc.apicadastro.domain.Pessoa;
import com.erc.apicadastro.repositories.ContatoRepository;
import com.erc.apicadastro.repositories.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ContatoRepository contatoRepository;

    public PessoaService(PessoaRepository pessoaRepository, ContatoRepository contatoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.contatoRepository = contatoRepository;
    }

    public Pessoa encontrarPorId(Integer pessoaId) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);
        return pessoaOptional.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Pessoa> encontrarTodos() {
        return pessoaRepository.findAll();
    }

    public Pessoa salvar(Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        contatoRepository.saveAll(pessoa.getContatos());
        return pessoaSalva;
    }

    public Pessoa atualizar(Pessoa pessoaAtualizada) {

        Pessoa pessoa = encontrarPorId(pessoaAtualizada.getId());

        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setNascimento(pessoaAtualizada.getNascimento());
        pessoa.setContatos(pessoaAtualizada.getContatos());
        // TODO salvar contatos
        return pessoaRepository.save(pessoa);
    }

    public void deletar(Integer id) {
        pessoaRepository.deleteById(id);
    }

}
