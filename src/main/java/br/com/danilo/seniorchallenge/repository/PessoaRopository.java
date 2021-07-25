package br.com.danilo.seniorchallenge.repository;

import br.com.danilo.seniorchallenge.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRopository extends JpaRepository<Pessoa, Long> {
}
