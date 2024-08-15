package main.psoft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

public class Biblioteca {

    private Map<String, Livro> livros;
    private Map<String, User> usuarios;
    private List<Reserva> reservas;

    public Biblioteca(){
        this.livros = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.reservas = new ArrayList<>();
    }

    public Livro retornaLivro(String titulo){
        Collection<Livro> livros = this.livros.values();
        for (Livro livro : livros){
            if (livro.getNome().toLowerCase().contains(titulo.toLowerCase())){
                return livro;
            }
        } return null;
    }

    public void addUser(String email){
        this.usuarios.put(email, new User(email));
    }

    public void addLivro(Livro livro){
        this.livros.put(livro.getNome(), livro);
    }

    public void addLivro(String titulo){
        this.livros.put(titulo, new Livro(titulo));
    }

    public List<String> getLivrosCadastrados(){
        List<String> livros = new ArrayList<>();
        for (Livro livro : this.livros.values()){
            livros.add(livro.getNome());
        }
        return livros;
    }

    public List<String> getLivrosDisponiveis(){
        List<String> livros = new ArrayList<>();
        for (Livro livro : this.livros.values()){
            if (livro.getDisponibilidade()){
                livros.add(livro.getNome());
            }
        }
        return livros;
    }

    public String reservaLivro(String emailUser, String tituloLivro){
        if (!this.usuarios.containsKey(emailUser)){
            throw new RuntimeErrorException(null, "Usuário não cadastrado.");
        }
        if (this.livros.get(tituloLivro).getDisponibilidade() == false){
            throw new RuntimeErrorException(null, "Livro indisponível.");
        }
        Reserva reserva = new Reserva(tituloLivro, emailUser);
        this.livros.get(tituloLivro).setDisponibilidade(false);
        this.reservas.add(reserva);
        return "Reserva bem sucedida do livro: " + this.livros.get(tituloLivro).getNome();
    }
}

