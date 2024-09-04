public class Done implements InterfaceState {

    private UserStory us;

    public Done(UserStory us){
        this.us = us;
    }

    @Override
    public void mover(Pessoa pessoa){
        //Não faz nada
    }
}
