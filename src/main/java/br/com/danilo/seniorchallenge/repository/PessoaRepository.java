package br.com.danilo.seniorchallenge.repository;

import br.com.danilo.seniorchallenge.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query(value = "select p.*\n" +
            "from pessoa p\n" +
            "where 1=1\n" +
            "and \n" +
            "(p.nome ilike '%'||:nome||'%')\n" +
            "and \n" +
            "(p.cpf ilike '%' || :cpf || '%')",
            countQuery = "SELECT count(*) \n" +
                    "from pessoa p\n" +
                    "where 1=1\n" +
                    "and \n" +
                    "(p.nome ilike '%'||:nome||'%')\n" +
                    "and \n" +
                    "(p.cpf ilike '%' || :cpf || '%')",
            nativeQuery = true
    )
    Page<Pessoa> getByFilterAndPage(@Param("nome") String nome, @Param("cpf") String cpf, Pageable page);
}
