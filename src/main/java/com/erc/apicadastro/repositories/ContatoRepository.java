package com.erc.apicadastro.repositories;

import com.erc.apicadastro.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {

    List<Contato> findByPessoaId(Integer pessoaId);

}
