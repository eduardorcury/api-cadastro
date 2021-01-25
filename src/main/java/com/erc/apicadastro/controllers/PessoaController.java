package com.erc.apicadastro.controllers;

import com.erc.apicadastro.dto.PessoaDTO;
import com.erc.apicadastro.mapper.PessoaMapper;
import com.erc.apicadastro.services.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodos() {
        List<PessoaDTO> pessoas = pessoaService.encontrarTodos();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> encontrarPorId(@PathVariable(value = "id") Integer pessoaId) {
        PessoaDTO pessoa = pessoaService.encontrarPorId(pessoaId);
        //TODO checar id errado
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody @Valid PessoaDTO pessoaDTO) {
        pessoaService.salvar(pessoaDTO);
        return new ResponseEntity<>("Pessoa cadastrada com sucesso", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable(value = "id") Integer pessoaId, @Valid PessoaDTO pessoaDTO) {
        pessoaService.atualizar(pessoaId, pessoaDTO);
        return new ResponseEntity<>("Pessoa atualizada com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable(value = "id") Integer pessoaId) {
        pessoaService.deletar(pessoaId);
    }

}
