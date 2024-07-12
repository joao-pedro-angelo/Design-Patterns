package dtos;

import java.util.List;

public record GetPessoaDTO(
        String nome,
        int idade,
        String cpf,
        List<String> enderecos,
        String profissao
) {
}
