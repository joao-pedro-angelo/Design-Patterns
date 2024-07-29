import java.util.HashMap;
import java.util.Map;

public class Sprint {

    private Map<Long, Funcionario> desenvolvedores;
    private Funcionario gerente;
    private Funcionario lider;

    public Sprint(Funcionario gerente, Funcionario lider){
        this.gerente = gerente;
        this.lider = lider;
        this.desenvolvedores = new HashMap<>();
    }

    public Funcionario getGerente() {
        return this.gerente;
    }

    public Funcionario getLider() {
        return this.lider;
    }

    public void setGerente(Funcionario gerente){
        this.gerente = gerente;
    }

    public void setLider(Funcionario lider){
        this.lider = lider;
    }

    public void addDesenvolvedor(Funcionario desenvolvedor){
        this.desenvolvedores.put(desenvolvedor.getId(), desenvolvedor);
    }

    public void removerDesenvolvedor(Long id){
        this.desenvolvedores.remove(id);
    }

    public String visualizarDesenvolvedor(){
        String result = "";
        for (Funcionario dev : this.desenvolvedores.values()){
            result += dev.getNome() + "\n";
        } return result;
    }

    public String toString(){
        String result = "";
        result += "Nome do Gerente: " + this.gerente.getNome() + "\n";
        result += "Nome do Líder: " + this.lider.getNome() + "\n";
        result += "Devs: " + this.visualizarDesenvolvedor();
        return result;
    }
}
