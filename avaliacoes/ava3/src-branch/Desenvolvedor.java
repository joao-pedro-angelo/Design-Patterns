import java.util.Random;

public class Desenvolvedor implements Funcionario{
    private String nome;
    private Long id;


    public Desenvolvedor(String nome){
        this.nome = nome;
        this.id = this.gerarId();
    }

    private Long gerarId(){
        Random random = new Random();
        return random.nextLong();
    }

    @Override
    public String getNome(){
        return this.nome;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
