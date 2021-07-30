package br.com.danilo.seniorchallenge.repository;

import br.com.danilo.seniorchallenge.model.Movimentacao;
import br.com.danilo.seniorchallenge.model.MovimentacaoDTO;
import br.com.danilo.seniorchallenge.model.Quarto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query(value = "select m.*\n" +
            "from movimentacao m\n",
//            "where q.locado = false\n",
            countQuery = "SELECT count(*) \n" +
                    "from movimentacao\n",
//                    "where q.locado = false\n",
            nativeQuery = true
    )
    Page<Movimentacao> getAllNotLocatedPaged(Pageable page);
}
