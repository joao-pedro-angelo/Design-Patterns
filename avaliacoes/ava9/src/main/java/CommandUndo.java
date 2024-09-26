public class CommandUndo implements Command{

    private Texto txt;

    public CommandUndo(Texto txt) {
        this.txt = txt;
    }

    @Override
    public void execute() {
        this.txt.undo();
    }
    
}
