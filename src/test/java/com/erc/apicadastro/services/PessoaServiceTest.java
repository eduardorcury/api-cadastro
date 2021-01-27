package com.erc.apicadastro.services;

import com.erc.apicadastro.domain.Pessoa;
import com.erc.apicadastro.dto.ContatoDTO;
import com.erc.apicadastro.dto.PessoaDTO;
import com.erc.apicadastro.exceptions.PessoaNaoEncontradaException;
import com.erc.apicadastro.mapper.PessoaMapper;
import com.erc.apicadastro.repositories.ContatoRepository;
import com.erc.apicadastro.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    private final PessoaMapper mapper = PessoaMapper.INSTANCE;

    @Mock
    PessoaRepository pessoaRepository;

    @Mock
    ContatoRepository contatoRepository;

    @InjectMocks
    PessoaService pessoaService;

    Calendar calendar = Calendar.getInstance();
    PessoaDTO pessoaDTO;
    ContatoDTO contatoDTO;
    Pessoa pessoa;

    @BeforeEach
    void setUp() {
        calendar.set(1998, Calendar.FEBRUARY, 3);
        contatoDTO = new ContatoDTO("Maria", "12345", "maria@gmail.com");
        pessoaDTO = new PessoaDTO("Eduardo", "454.338.320-66", calendar.getTime(),
                List.of(contatoDTO));
        pessoa = mapper.toDomain(pessoaDTO);
        pessoa.setId(1);
    }

    @Test
    @DisplayName("Teste encontrar por ID válido")
    void encontrarPorIdValido() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        PessoaDTO pessoaEncontrada = pessoaService.encontrarPorId(1);
        assertThat(pessoaEncontrada.getNome(), is(equalTo(pessoaDTO.getNome())));
        assertThat(pessoaEncontrada.getCpf(), is(equalTo(pessoaDTO.getCpf())));
        assertThat(pessoaEncontrada.getNascimento(), is(equalTo(pessoaDTO.getNascimento())));
        assertThat(pessoaEncontrada.getContatos().get(0).getNome(), is(equalTo(contatoDTO.getNome())));
        assertThat(pessoaEncontrada.getContatos().get(0).getTelefone(), is(equalTo(contatoDTO.getTelefone())));
        assertThat(pessoaEncontrada.getContatos().get(0).getEmail(), is(equalTo(contatoDTO.getEmail())));
    }

    @Test
    @DisplayName("Teste encontrar por ID inválido")
    void encontrarPorIdInvalido() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PessoaNaoEncontradaException.class,
                () -> pessoaService.encontrarPorId(1));
    }

    @Test
    @DisplayName("Teste encontrar todos")
    void encontrarTodos() {
        when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(pessoa));
        List<PessoaDTO> lista = pessoaService.encontrarTodos();
        assertThat(lista.size(), is(equalTo(1)));
        assertThat(lista.get(0).getNome(), is(equalTo(pessoaDTO.getNome())));
        assertThat(lista.get(0).getCpf(), is(equalTo(pessoaDTO.getCpf())));
        assertThat(lista.get(0).getNascimento(), is(equalTo(pessoaDTO.getNascimento())));
    }

    @Test
    @DisplayName("Teste salvar novo usuário")
    void salvar() {
        pessoa.setId(null);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(contatoRepository.saveAll(pessoa.getContatos())).thenReturn(pessoa.getContatos());
        PessoaDTO pessoaSalva = pessoaService.salvar(pessoaDTO);
        assertThat(pessoaSalva.getNome(), is(equalTo(pessoaDTO.getNome())));
        assertThat(pessoaSalva.getCpf(), is(equalTo(pessoaDTO.getCpf())));
        assertThat(pessoaSalva.getNascimento(), is(equalTo(pessoaDTO.getNascimento())));
        assertThat(pessoaSalva.getContatos().get(0).getNome(), is(equalTo(contatoDTO.getNome())));
        assertThat(pessoaSalva.getContatos().get(0).getTelefone(), is(equalTo(contatoDTO.getTelefone())));
        assertThat(pessoaSalva.getContatos().get(0).getEmail(), is(equalTo(contatoDTO.getEmail())));
    }

    @Test
    @DisplayName("Teste atualizar com ID válido")
    void atualizarComIdValido() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaDTO pessoaAtualizada = pessoaService.atualizar(1, pessoaDTO);
        assertThat(pessoaAtualizada.getNome(), is(equalTo(pessoaDTO.getNome())));
        assertThat(pessoaAtualizada.getCpf(), is(equalTo(pessoaDTO.getCpf())));
        assertThat(pessoaAtualizada.getNascimento(), is(equalTo(pessoaDTO.getNascimento())));
        assertThat(pessoaAtualizada.getContatos().get(0).getNome(), is(equalTo(contatoDTO.getNome())));
        assertThat(pessoaAtualizada.getContatos().get(0).getTelefone(), is(equalTo(contatoDTO.getTelefone())));
        assertThat(pessoaAtualizada.getContatos().get(0).getEmail(), is(equalTo(contatoDTO.getEmail())));
    }

    @Test
    @DisplayName("Teste atualizar com ID inválido")
    void atualizarComIdInvalido() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PessoaNaoEncontradaException.class,
                () -> pessoaService.atualizar(1, pessoaDTO));
    }

    @Test
    @DisplayName("Teste deletar usuário com ID válido")
    void deletarComIdValido() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        doNothing().when(pessoaRepository).deleteById(1);
        pessoaService.deletar(1);
        verify(pessoaRepository, times(1)).findById(1);
        verify(pessoaRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Teste deletar usuário com ID inválido")
    void deletarComIdInvalido() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PessoaNaoEncontradaException.class,
                () -> pessoaService.deletar(1));
    }

}





