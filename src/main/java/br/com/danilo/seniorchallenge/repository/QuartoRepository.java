package br.com.danilo.seniorchallenge.repository;

import br.com.danilo.seniorchallenge.model.Pessoa;
import br.com.danilo.seniorchallenge.model.Quarto;
import br.com.danilo.seniorchallenge.model.QuartoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    @Query(value = "select q.*\n" +
            "from quarto q\n" +
            "where q.locado = false\n",
            countQuery = "SELECT count(*) \n" +
                    "from quarto q\n" +
                    "where q.locado = false\n",
            nativeQuery = true
    )
    Page<Quarto> getAllNotLocatedPaged(Pageable page);
}
