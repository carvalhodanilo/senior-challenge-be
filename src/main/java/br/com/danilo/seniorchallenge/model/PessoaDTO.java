package br.com.danilo.seniorchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private long id;
    private String nome;
    private String cpf;
    private String telefone;

    public static PessoaDTO converter(Pessoa p){
        var pessoa = new PessoaDTO();
        pessoa.setId(p.getId());
        pessoa.setNome(p.getNome());
        pessoa.setCpf(p.getCpf());
        pessoa.setTelefone(p.getTelefone());
        return pessoa;
    }
}
