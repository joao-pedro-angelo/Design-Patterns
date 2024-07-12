package services;

import dtos.AtualizarPessoaDTO;
import dtos.CreatePessoaDTO;
import dtos.GetPessoaDTO;
import repositorios.RepositorioPessoa;

import java.util.List;

public class PessoaService {

    private RepositorioPessoa repositorioPessoa;

    public PessoaService(RepositorioPessoa repositorioPessoa) {
        this.repositorioPessoa = repositorioPessoa;
    }

    public void adicionarPessoa(String nome, String cpf,
                                int idade, String endereco,
                                String telefone, String profissao) {
        CreatePessoaDTO createPessoaDTO = new CreatePessoaDTO(
                nome, cpf, idade, endereco, telefone, profissao);
        this.repositorioPessoa.cadastrar(createPessoaDTO);
    }

    public void atualizarPessoa(int idade, String endereco,
                                String telefone, String profissao){
        AtualizarPessoaDTO atualizarPessoaDTO = new AtualizarPessoaDTO(
                idade, endereco, telefone, profissao);
    }

    public void removerPessoa(String cpf){
        this.repositorioPessoa.remover(cpf);
    }

    public GetPessoaDTO getPessoa(String cpf){
        return this.repositorioPessoa.buscar(cpf);
    }

    public List<GetPessoaDTO> getPessoas(){
        return this.repositorioPessoa.allPessoas();
    }
}
