public class CommandImprimir implements Command{

    private Texto txt;

    public CommandImprimir(Texto txt) {
        this.txt = txt;
    }

    @Override
    public void execute() {
        this.txt.imprimir();
    }
    
}
