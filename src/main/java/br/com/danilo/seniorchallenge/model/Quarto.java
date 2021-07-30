package br.com.danilo.seniorchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private Long preco;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "locado")
    private boolean locado;

    @OneToMany( mappedBy = "quarto" )
    private List<Movimentacao> movimentacoes = new ArrayList<>();
}
