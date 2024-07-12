package entidades;

import dtos.AtualizarPessoaDTO;
import dtos.CreatePessoaDTO;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {

    private String nome;
    private int idade;
    private String cpf;
    private List<String> enderecos = new ArrayList<>();
    private String profissao;

    public Pessoa(String nome, int idade, String cpf, String enderecos, String profissao) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.enderecos.add(enderecos);
        this.profissao = profissao;
    }

    public Pessoa(CreatePessoaDTO createPessoaDTO){
        this.nome = createPessoaDTO.nome();
        this.idade = createPessoaDTO.idade();
        this.cpf = createPessoaDTO.cpf();
        this.profissao = createPessoaDTO.profissao();
        this.enderecos.add(createPessoaDTO.endereco());
    }

    public void atualizarPessoa(AtualizarPessoaDTO atualizarPessoaDTO){
        this.setIdade(atualizarPessoaDTO.idade());
        this.setEndereco(atualizarPessoaDTO.endereco());
        this.setProfissao(atualizarPessoaDTO.profissao());
    }

    public void setEndereco(String endereco){
        this.enderecos.add(endereco);
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public String getCpf() {
        return this.cpf;
    }

    public List<String> getEnderecos() {
        return this.enderecos.stream().toList();
    }

    public String getProfissao() {
        return this.profissao;
    }
}
