import java.util.ArrayList;
import java.util.List;

public class Texto {
    
    private List<String> paragrafos;

    public Texto(){
        this.paragrafos = new ArrayList<>();
    }

    public void addParagrafo(String s){
        this.paragrafos.add(s);
    }

    public void removerParagrafo(String s){
        this.paragrafos.remove(s);
    }

    public void undo(){
        if (!paragrafos.isEmpty()) {
            this.paragrafos.remove(this.paragrafos.size() - 1);
        }
    }

    // Imprime o conteúdo do documento
    public void imprimir(){
        for(String s : this.paragrafos){
            System.out.println("> " + s);
        }
        System.out.println();
    }
}
