package br.com.danilo.seniorchallenge.controller;


import br.com.danilo.seniorchallenge.model.Pessoa;
import br.com.danilo.seniorchallenge.model.PessoaDTO;
import br.com.danilo.seniorchallenge.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping("/pessoas")
    public ResponseEntity<Page<PessoaDTO>> getByFilterAndPage(Pageable page,
                                                           @RequestParam(required = false) Optional<String> nome,
                                                           @RequestParam(required = false) Optional<String> cpf) {
        try {
            var p = nome.isPresent() ? nome.get() : "";
            var p2 = cpf.isPresent() ? cpf.get() : "";

            var pessoas = pessoaService
                    .getByFilterAndPage(nome.isPresent() ? nome.get() : "",
                                        cpf.isPresent() ? cpf.get() : "",
                                        page);

            var pessoasDTO  = new PageImpl<>(pessoas
                    .stream()
                    .map(person -> PessoaDTO.converter(person))
                    .collect(Collectors.toList()), page, pessoas.getTotalElements());

            if (pessoas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(pessoasDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaDTO> createPessoa(@RequestBody Pessoa pessoa) {
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

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable("id") Long id) {
        Optional<Pessoa> dadosPessoa = pessoaService.findById(id);

        if (dadosPessoa.isPresent()) {
            return new ResponseEntity<>(PessoaDTO.converter(dadosPessoa.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/pessoas/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable("id") Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> dadosPessoa = pessoaService.findById(id);

        if (dadosPessoa.isPresent()) {
            Pessoa p = dadosPessoa.get();
            p.setNome(pessoa.getNome());
            p.setCpf(pessoa.getCpf());
            p.setTelefone(pessoa.getTelefone());

            return new ResponseEntity<>(PessoaDTO.converter(pessoaService.save(p)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("id") long id) {
        try {
            pessoaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
