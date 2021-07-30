package br.com.danilo.seniorchallenge.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY, cascade=CascadeType.MERGE )
    @JoinColumn(name = "ID_QUARTO", referencedColumnName = "ID")
    private Quarto quarto;

    @ManyToOne( fetch = FetchType.LAZY, cascade=CascadeType.MERGE )
    @JoinColumn(name = "ID_HOSPEDE", referencedColumnName = "ID")
    private Pessoa pessoa;

    @Column(name = "entrada")
    private Date entrada;

    @Column(name = "saida")
    private Date saida;

    @Column(name = "garagem")
    private boolean garagem;

    @Column(name = "closed")
    private boolean closed;

    @Column(name = "valorTotal")
    private Long valorTotal;


}
