package dtos;

public record CreatePessoaDTO(
        String nome,
        String cpf,
        int idade,
        String endereco,
        String telefone,
        String profissao
) {
}
