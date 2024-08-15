package test.psoft;

import org.junit.jupiter.api.Test;

import main.psoft.Biblioteca;
import main.psoft.Livro;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class BibliotecaTest {

    private Biblioteca biblioteca;
    private Livro livro;
    private Livro livroIndisponivel;

    @BeforeEach
    public void setUp(){
        this.biblioteca = new Biblioteca();
        this.livro = new Livro("Nome do livro");
        this.livroIndisponivel = new Livro("Livro indisponível");
        this.livroIndisponivel.setDisponibilidade(false);

        biblioteca.addUser("joao.pedro@gmail.com");
        biblioteca.addUser("coisa.peppa@gmail.com");
    
        biblioteca.addLivro("Senhor dos Anéis");
        biblioteca.addLivro(this.livro);
        biblioteca.addLivro(this.livroIndisponivel);
    }

    @Test
    public void testBuscaLivroExistentePeloTituloTotal() {
        assertEquals(this.livro, this.biblioteca.retornaLivro("Nome do livro"));
    }

    @Test
    public void testBuscaLivroExistentePeloTituloParcial(){
        assertEquals(this.livro, this.biblioteca.retornaLivro("Nome do"));
    }

    @Test
    public void testBuscaLivroInexistente(){
        assertNull(this.biblioteca.retornaLivro("não existo"));
    }

    @Test
    public void testGetLivros() {
        List<String> livros = this.biblioteca.getLivrosCadastrados();
        assertNotNull(livros);
        assertTrue(livros.contains("Senhor dos Anéis"));
        assertTrue(livros.contains(this.livro.getNome()));
        assertTrue(livros.contains("Livro indisponível"));
    }

    @Test
    public void testGetLivrosDisponiveis(){
        List<String> livros = this.biblioteca.getLivrosDisponiveis();
        assertNotNull(livros);
        assertTrue(livros.contains("Senhor dos Anéis"));
        assertTrue(livros.contains(this.livro.getNome()));
        assertFalse(livros.contains("Livro indisponível"));
    }

    @Test
    public void testReservaLivroDisponivelUsuarioOK() {
        String result = this.biblioteca.reservaLivro("joao.pedro@gmail.com", "Senhor dos Anéis");
        assertEquals("Reserva bem sucedida do livro: Senhor dos Anéis", result);
    }

    @Test
    public void testReservaLivroDisponivelUsuarioNaoCadastrado(){
        RuntimeException ex = assertThrows
        (RuntimeException.class, 
        () -> this.biblioteca.reservaLivro("naoCadastrado@gmail.com",
        "Senhor dos Anéis"));
        assertTrue(ex.getMessage().contains("Usuário não cadastrado."));
    }

    @Test
    public void testReservaLivroIndisponivel(){
        RuntimeException ex = assertThrows
        (RuntimeException.class, 
        () -> this.biblioteca.reservaLivro("joao.pedro@gmail.com",
        "Livro indisponível"));
        assertTrue(ex.getMessage().contains("Livro indisponível."));
    }
}