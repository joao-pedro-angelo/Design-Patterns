public class CommandAdd implements Command{

    private Texto txt;
    private String paragrafo;

    public CommandAdd(Texto txt, String paragrafo) {
        this.txt = txt;
        this.paragrafo = paragrafo;
    }

    @Override
    public void execute() {
        this.txt.addParagrafo(this.paragrafo);
    }
}
