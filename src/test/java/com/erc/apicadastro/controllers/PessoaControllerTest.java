package com.erc.apicadastro.controllers;

import com.erc.apicadastro.dto.ContatoDTO;
import com.erc.apicadastro.dto.PessoaDTO;
import com.erc.apicadastro.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
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

    PessoaDTO pessoaValida;

    @BeforeEach
    void setUp() {
        pessoaValida = new PessoaDTO("Eduardo", "454.338.320-66", new Date("03-02-1998"),
                List.of(new ContatoDTO("Maria", "12345", "maria@gmail.com")));
    }

    @Test
    void listarTodos() throws Exception {
        when(pessoaService.encontrarTodos()).thenReturn(Collections.singletonList(pessoaValida));
        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(pessoaValida))));

    }

    @Test
    void encontrarPorIdValido() throws Exception {
        when(pessoaService.encontrarPorId(1)).thenReturn(pessoaValida);
        mockMvc.perform(get(API_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaValida)));
    }

    @Test
    void encontrarPorIdInvalido() throws Exception {
        when(pessoaService.encontrarPorId(1)).thenThrow(new RuntimeException());
        mockMvc.perform(get(API_URL + "/1"))
                .andExpect(status().isBadRequest());
    }
}