public class InProgress implements InterfaceState{

    private UserStory us;

    public InProgress(UserStory us){
        this.us = us;
    }

    @Override
    public void mover(Pessoa pessoa){
        if(!pessoa.isScrumMaster()){
            this.us.setState(new ToVerify(this.us));
        }
    }
}
