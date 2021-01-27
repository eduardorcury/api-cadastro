package com.erc.apicadastro.controllers;

import com.erc.apicadastro.dto.PessoaDTO;
import com.erc.apicadastro.services.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodos() {
        List<PessoaDTO> pessoas = pessoaService.encontrarTodos();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> encontrarPorId(@PathVariable(value = "id") Integer pessoaId) {
        PessoaDTO pessoa = pessoaService.encontrarPorId(pessoaId);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/busca")
    public ResponseEntity<Page<PessoaDTO>> buscaPaginada(
            @RequestParam(value = "pag", defaultValue = "0") Integer numeroPagina,
            @RequestParam(value = "qtd", defaultValue = "20") Integer quantidadePorPagina,
            @RequestParam(value = "ordem", defaultValue = "nome") String campoOrdenacao,
            @RequestParam(value = "dir", defaultValue = "ASC") String direcaoOrdenacao) {

        Page<PessoaDTO> pagina = pessoaService.buscaPaginada(numeroPagina, quantidadePorPagina, campoOrdenacao, direcaoOrdenacao);
        return new ResponseEntity<>(pagina, HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<PessoaDTO> salvar(@RequestBody @Valid PessoaDTO pessoaDTO) {
        PessoaDTO pessoa = pessoaService.salvar(pessoaDTO);
        return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable(value = "id") Integer pessoaId, @RequestBody @Valid PessoaDTO pessoaDTO) {
        PessoaDTO pessoa = pessoaService.atualizar(pessoaId, pessoaDTO);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable(value = "id") Integer pessoaId) {
        pessoaService.deletar(pessoaId);
    }

}
