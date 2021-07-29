package br.com.danilo.seniorchallenge.controller;


import br.com.danilo.seniorchallenge.model.QuartoDTO;
import br.com.danilo.seniorchallenge.model.Quarto;
import br.com.danilo.seniorchallenge.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuartoController {

    @Autowired
    QuartoService quartoService;

    @GetMapping("/quartos")
    public ResponseEntity<Page<Quarto>> getAllNotLocatedPaged(Pageable page) {
        try {
            var quartos = quartoService
                    .getAllNotLocatedPaged(page);
            if (quartos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(quartos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/quartos")
    public ResponseEntity<QuartoDTO> createQuarto(@RequestBody QuartoDTO quarto) {
        try {
            var q = new Quarto();
            q.setNome(quarto.getNome());
            q.setImagem(quarto.getImagem());
            q.setPreco(quarto.getPreco());
            q.setLocado(quarto.isLocado());

            quartoService.saveAndFlush(q);
            return new ResponseEntity<>(QuartoDTO.converter(q), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/quartos/{id}")
    public ResponseEntity<QuartoDTO> getQuartoById(@PathVariable("id") Long id) {
        Optional<Quarto> dadosQuarto = quartoService.findById(id);

        if (dadosQuarto.isPresent()) {
            return new ResponseEntity<>(QuartoDTO.converter(dadosQuarto.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/quartos/{id}")
    public ResponseEntity<QuartoDTO> updateQuarto(@PathVariable("id") Long id, @RequestBody Quarto quarto) {
        Optional<Quarto> dadosQuarto = quartoService.findById(id);

        if (dadosQuarto.isPresent()) {
            var q = new Quarto();
            q.setNome(quarto.getNome());
            q.setImagem(quarto.getImagem());
            q.setPreco(quarto.getPreco());
            q.setLocado(quarto.isLocado());

            return new ResponseEntity<>(QuartoDTO.converter(quartoService.save(q)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/quartos/{id}")
    public ResponseEntity<HttpStatus> deletequarto(@PathVariable("id") long id) {
        try {
            quartoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
