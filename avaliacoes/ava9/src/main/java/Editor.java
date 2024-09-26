public class Editor {

    private Command comando;
    private Texto txt;

    public Editor(Texto texto) {
        this.comando = new CommandImprimir(texto);
        this.txt = texto;
    }

    public void setComando(Command c){
        this.comando = c;
    }

    public void execute(){
        this.comando.execute();
    }

    public void undo(){
        new CommandUndo(this.txt).execute();;
    }
}
