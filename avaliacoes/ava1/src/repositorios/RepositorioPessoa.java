package repositorios;

import dtos.AtualizarPessoaDTO;
import dtos.CreatePessoaDTO;
import dtos.GetPessoaDTO;

import java.util.List;

public interface RepositorioPessoa {
    void cadastrar(CreatePessoaDTO pessoa);
    void atualizar(AtualizarPessoaDTO pessoa, String cpf);
    void remover(String cpf);
    GetPessoaDTO buscar(String cpf);
    List<GetPessoaDTO> allPessoas();
}
