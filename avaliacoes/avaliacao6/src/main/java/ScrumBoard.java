import java.util.ArrayList;
import java.util.List;

public class ScrumBoard {
    private List<Pessoa> pessoas;
    private List<UserStory> UserStories;

    public ScrumBoard(){
        this.pessoas = new ArrayList<>();
        this.UserStories = new ArrayList<>();
    }

    public void criarUS(String id){
        this.UserStories.add(new UserStory(id));
    }

    public UserStory getUserStory(String id){
        UserStory temp = new UserStory(id);

        for(UserStory u : this.UserStories){
            if (u.equals(temp)){
                return u;
            }
        }
        return null;
    }

    public void addDesenvolvedor(String id){
        this.pessoas.add(new Pessoa(id, Cargo.Desenvolvedor));
    }

    public void addScrumMaster(String id){
        this.pessoas.add(new Pessoa(id, Cargo.ScrumMaster));
    }

    public Pessoa getPessoa(String id){
        Pessoa temp = new Pessoa(id, Cargo.Desenvolvedor);

        for (Pessoa pessoa : this.pessoas){
            if (pessoa.equals(temp)){
                return pessoa;
            }
        }

        return null;
    }

    public void moverUs(String idUS, String idPessoa){
        UserStory us = this.getUserStory(idUS);
        Pessoa p = this.getPessoa(idPessoa);

        if (us != null && p != null){
            us.mover(p);
        }
    }
}
