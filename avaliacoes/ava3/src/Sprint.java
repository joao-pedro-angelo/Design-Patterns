import java.util.List;

public class Sprint {

    private List<Desenvolvedor> desenvolvedores;
    private Gerente gerente;
    private Lider lider;

    public void defineEquipe(Gerente gerente, Lider lider, List<Desenvolvedor> desenvolvedores) {
        setGerente(gerente);
        setLider(lider);
        setDevs(desenvolvedores);
    }

    private void setGerente(Gerente gerente){
        this.gerente = gerente;
    }

    private void setLider(Lider lider){
        this.lider = lider;
    }

    public void setDevs(List<Desenvolvedor> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }
}
