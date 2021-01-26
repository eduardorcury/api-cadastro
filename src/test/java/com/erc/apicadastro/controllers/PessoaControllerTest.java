package com.erc.apicadastro.controllers;

import com.erc.apicadastro.dto.ContatoDTO;
import com.erc.apicadastro.dto.PessoaDTO;
import com.erc.apicadastro.exceptions.PessoaNaoEncontradaException;
import com.erc.apicadastro.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PessoaController.class)
@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    private final String API_URL = "/api/v1/pessoas";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PessoaService pessoaService;

    Validator validator;
    Calendar calendar = Calendar.getInstance();
    PessoaDTO pessoaValida;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        calendar.set(1998, Calendar.FEBRUARY, 3);
        pessoaValida = new PessoaDTO("Eduardo", "454.338.320-66", calendar.getTime(),
                List.of(new ContatoDTO("Maria", "12345", "maria@gmail.com")));
    }

    @Test
    @DisplayName("Teste GET")
    void listarTodos() throws Exception {
        when(pessoaService.encontrarTodos()).thenReturn(Collections.singletonList(pessoaValida));
        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(pessoaValida))));

    }

    @Test
    @DisplayName("Teste GET paginado")
    void paginacao() throws Exception {
        when(pessoaService.buscaPaginada(1, 1, "nome", "ASC"))
                .thenReturn(new PageImpl<>(List.of(pessoaValida)));
        mockMvc.perform(get(API_URL + "/busca?pag=1&qtd=1&ordem=nome&dir=ASC"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste GET /id válido")
    void encontrarPorIdValido() throws Exception {
        when(pessoaService.encontrarPorId(1)).thenReturn(pessoaValida);
        mockMvc.perform(get(API_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaValida)));
    }

    @Test
    @DisplayName("Teste GET /id inválido")
    void encontrarPorIdInvalido() throws Exception {
        when(pessoaService.encontrarPorId(1)).thenThrow(new PessoaNaoEncontradaException(""));
        mockMvc.perform(get(API_URL + "/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste POST válido")
    void salvarComPessoaValida() throws Exception {
        when(pessoaService.salvar(pessoaValida)).thenReturn(pessoaValida);
        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Teste POST inválido")
    void salvarComPessoaInvalida() throws Exception {
        PessoaDTO pessoaInvalida = new PessoaDTO(" ", " ", null, Collections.emptyList());
        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste PUT válido")
    void atualizarComPessoaValida() throws Exception {
        when(pessoaService.atualizar(1, pessoaValida)).thenReturn(pessoaValida);
        mockMvc.perform(put(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste PUT inválido")
    void atualizarComPessoaInvalida() throws Exception {
        PessoaDTO pessoaInvalida = new PessoaDTO(" ", " ", null, Collections.emptyList());
        mockMvc.perform(put(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste DELETE válido")
    void deletarComPessoaValida() throws Exception {
        doNothing().when(pessoaService).deletar(1);
        mockMvc.perform(delete(API_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Teste DELETE inválido")
    void deletarComPessoaInvalida() throws Exception {
        doThrow(new PessoaNaoEncontradaException(" ")).when(pessoaService).deletar(1);
        mockMvc.perform(delete(API_URL + "/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste validação: Pessoa com nome em branco")
    void pessoaComNomeEmBranco() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.setNome(" ");
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Pessoa com CPF em branco")
    void pessoaComCPFEmBranco() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.setCpf(null);
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Pessoa com CPF inválido")
    void pessoaComCPFInvalido() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.setCpf("123");
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Pessoa com data em branco")
    void pessoaComDataEmBranco() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.setNascimento(null);
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Pessoa com data no futuro")
    void pessoaComDataNoFuturo() {
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.setNascimento(calendar.getTime());
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Pessoa sem contatos")
    void pessoaSemContato() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.setContatos(Collections.emptyList());
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Contato com nome em branco")
    void contatoComNomeEmBranco() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.getContatos().forEach(contatoDTO -> contatoDTO.setNome(" "));
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Contato com telefone em branco")
    void contatoComTelefoneEmBranco() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.getContatos().forEach(contatoDTO -> contatoDTO.setTelefone("    "));
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Contato com telefone inválido")
    void contatoComTelefoneInvalido() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.getContatos().forEach(contatoDTO -> contatoDTO.setTelefone("123"));
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Contato com email em branco")
    void contatoComEmailEmBranco() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.getContatos().forEach(contatoDTO -> contatoDTO.setEmail(null));
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste validação: Contato com email inválido")
    void contatoComEmailInvalido() {
        PessoaDTO pessoaDTO = pessoaValida;
        pessoaDTO.getContatos().forEach(contatoDTO -> contatoDTO.setEmail("teste"));
        Set<ConstraintViolation<PessoaDTO>> exceptions = validator.validate(pessoaDTO);
        assertThat(exceptions.size()).isEqualTo(1);
    }

}