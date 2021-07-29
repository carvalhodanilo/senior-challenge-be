package br.com.danilo.seniorchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoDTO {

    private long id;
    private String nome;
    private Long preco;
    private String imagem;
    private boolean locado;

    public static QuartoDTO converter(Quarto q){
        var quarto = new QuartoDTO();
        quarto.setId(q.getId());
        quarto.setNome(q.getNome());
        quarto.setPreco(q.getPreco());
        quarto.setImagem(q.getImagem());
        quarto.setLocado(q.isLocado());
        return quarto;
    }
}
