package repositorios;

import dtos.AtualizarPessoaDTO;
import dtos.CreatePessoaDTO;
import dtos.GetPessoaDTO;
import entidades.Pessoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioPessoaIMPL implements RepositorioPessoa{

    private HashMap<String, Pessoa> pessoasRepositorio = new HashMap<>();

    @Override
    public void cadastrar(CreatePessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa(pessoaDTO);
        this.pessoasRepositorio.put(pessoa.getCpf(), pessoa);
    }

    @Override
    public void atualizar(AtualizarPessoaDTO pessoa, String cpf) {
        this.pessoasRepositorio.get(cpf).atualizarPessoa(pessoa);
    }

    @Override
    public void remover(String cpf) {
        this.remover(cpf);
    }

    @Override
    public GetPessoaDTO buscar(String cpf) {
        Pessoa pessoa = this.pessoasRepositorio.get(cpf);
        return new GetPessoaDTO(pessoa.getNome(), pessoa.getIdade(), pessoa.getCpf(),
                pessoa.getEnderecos(), pessoa.getProfissao());
    }

    @Override
    public List<GetPessoaDTO> allPessoas() {
        List<GetPessoaDTO> pessoasDTO = new ArrayList<>();
        for (Pessoa pessoa : this.pessoasRepositorio.values()) {
            pessoasDTO.add(new GetPessoaDTO(pessoa.getNome(), pessoa.getIdade(), pessoa.getCpf(),
                    pessoa.getEnderecos(), pessoa.getProfissao()));
        } return pessoasDTO;
    }
}
