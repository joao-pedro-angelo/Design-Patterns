public class ToVerify implements InterfaceState {

    private UserStory us;

    public ToVerify(UserStory us){
        this.us = us;
    }

    @Override
    public void mover(Pessoa pessoa){
        if (pessoa.isScrumMaster()){
            if (us.verificaAprovacao()){
                us.setState(new Done(this.us));
            } else{
                us.setState(new ToDo(this.us));
            }
        }
    }
}
