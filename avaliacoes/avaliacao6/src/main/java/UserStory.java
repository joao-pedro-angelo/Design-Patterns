public class UserStory{
    private InterfaceState state;
    private boolean aprovacao;
    private String id;

    public UserStory(String id){
        this.state = new ToDo(this);
        this.aprovacao = false;
        this.id = id;
    }

    public void setState(InterfaceState state){
        this.state = state;
    }

    public void mover(Pessoa pessoa){
        this.state.mover(pessoa);
    }

    public boolean verificaAprovacao(){
        return this.aprovacao;
    }

    public void aprovar(Pessoa pessoa){
        if (pessoa.isScrumMaster() && state instanceof ToVerify){
            this.aprovacao = true;
        }
    }

    public String getId(){
        return this.id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserStory other = (UserStory) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}