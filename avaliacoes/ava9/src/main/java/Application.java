public class Application {

    private Editor e;

    public Application() {
        this.e = new Editor(new Texto());
    }

    public void executarComando(Command comando) {
        this.e.setComando(comando);
        this.e.execute();
    }

    public void undo(){
        this.e.undo();
    }
}
