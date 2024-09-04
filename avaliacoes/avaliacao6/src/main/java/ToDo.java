public class ToDo implements InterfaceState {

    private UserStory us;

    public ToDo(UserStory us){
        this.us = us;
    }

    @Override
    public void mover(Pessoa pessoa){
        this.us.setState(new InProgress(this.us));
    }
}
