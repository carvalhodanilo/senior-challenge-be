package br.com.danilo.seniorchallenge.controller;


import br.com.danilo.seniorchallenge.model.Movimentacao;
import br.com.danilo.seniorchallenge.model.MovimentacaoDTO;
import br.com.danilo.seniorchallenge.model.PessoaDTO;
import br.com.danilo.seniorchallenge.model.Quarto;
import br.com.danilo.seniorchallenge.service.MovimentacaoService;
import br.com.danilo.seniorchallenge.service.QuartoService;
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
public class MovimentacaoController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @Autowired
    QuartoService quartoService;

    @GetMapping("/movimentacoes")
    public ResponseEntity<Page<MovimentacaoDTO>> getAll(Pageable page) {
        try {
            var movimentacoes = movimentacaoService
                    .getAllNotLocatedPaged(page);

            var movimentacoesDTO = new PageImpl<>(movimentacoes
                    .stream()
                    .map(mov -> MovimentacaoDTO.converter(mov))
                    .collect(Collectors.toList()), page, movimentacoes.getTotalElements());

            if (movimentacoes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(movimentacoesDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/movimentacoes")
    public ResponseEntity<MovimentacaoDTO> createMovimentacao(@RequestBody Movimentacao movimentacao) {
        try {
            movimentacao.setClosed(false);
            var q = movimentacao.getQuarto();
            q.setLocado(true);
            quartoService.save(q);

            movimentacaoService.saveAndFlush(movimentacao);
            return new ResponseEntity<>(MovimentacaoDTO.converter(movimentacao), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movimentacoes/{id}")
    public ResponseEntity<MovimentacaoDTO> getMovimentacaoById(@PathVariable("id") Long id) {
        Optional<Movimentacao> dadosMovimentacao = movimentacaoService.findById(id);

        if (dadosMovimentacao.isPresent()) {
            return new ResponseEntity<>(MovimentacaoDTO.converter(dadosMovimentacao.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/movimentacoes/{id}")
    public ResponseEntity<MovimentacaoDTO> updateMovimentacao(@PathVariable("id") Long id, @RequestBody Movimentacao movimentacao) {
        try {
            Optional<Movimentacao> dadosQuarto = movimentacaoService.findById(id);
            System.out.println(id);
            if (dadosQuarto.isPresent()) {
                dadosQuarto.get().setId(movimentacao.getId());
                dadosQuarto.get().setPessoa(movimentacao.getPessoa());
                dadosQuarto.get().setQuarto(movimentacao.getQuarto());
                dadosQuarto.get().setEntrada(movimentacao.getEntrada());
                dadosQuarto.get().setClosed(movimentacao.isClosed());
                dadosQuarto.get().setSaida(movimentacao.getSaida());
                dadosQuarto.get().setGaragem(movimentacao.isGaragem());

                return new ResponseEntity<>(MovimentacaoDTO.converter(movimentacaoService.save(movimentacao)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/movimentacoes/{id}")
    public ResponseEntity<HttpStatus> deleteMovimentacao(@PathVariable("id") long id) {
        try {
            movimentacaoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
