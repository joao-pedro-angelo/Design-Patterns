package controllers;

import dtos.GetPessoaDTO;
import repositorios.RepositorioPessoaIMPL;
import services.PessoaService;

import java.util.List;

public class ControllerPessoa {

    private PessoaService service = new PessoaService(new RepositorioPessoaIMPL());

    public void criarPessoa(String nome, String cpf, int idade,
                            String endereco, String telefone, String profissao){
        this.service.adicionarPessoa(nome, cpf, idade, endereco, telefone, profissao);
    }

    public void removerPessoa(String cpf){
        this.service.removerPessoa(cpf);
    }

    public void atualizarPessoa(int idade, String endereco, String telefone, String profissao){
        this.service.atualizarPessoa(idade, endereco, telefone, profissao);
    }

    public GetPessoaDTO getPessoaDTO(String cpf){
        return this.service.getPessoa(cpf);
    }

    public List<GetPessoaDTO> allPessoas(){
        return this.service.getPessoas();
    }
}
