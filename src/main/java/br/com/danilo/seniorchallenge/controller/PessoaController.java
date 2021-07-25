package br.com.danilo.seniorchallenge.controller;


import br.com.danilo.seniorchallenge.model.Pessoa;
import br.com.danilo.seniorchallenge.model.PessoaDTO;
import br.com.danilo.seniorchallenge.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDTO>> getAllPersons() {
        try {
            var pessoas = pessoaService
                    .findAll()
                    .stream()
                    .map(PessoaDTO::converter)
                    .collect(Collectors.toList());

            if (pessoas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaDTO> createPessoa(@RequestBody PessoaDTO pessoa) {
        try {
            var p = new Pessoa();
            p.setNome(pessoa.getNome());
            p.setCpf(pessoa.getCpf());
            p.setTelefone(pessoa.getTelefone());

            pessoaService.saveAndFlush(p);
            return new ResponseEntity<>(PessoaDTO.converter(p), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
