import java.util.List;

public class Cliente {

    private Strategy ordena;

    public Cliente(){
        this.ordena = new BubbleSort();
    }

    public void setBubble(){
        this.ordena = new BubbleSort();
    }

    public void setInsertion(){
        this.ordena = new InsertionSort();
    }

    public void setMerge(){
        this.ordena = new MergeSort();
    }

    public void setQuick(){
        this.ordena = new QuickSort();
    }

    public List<Integer> ordena(List<Integer> elementos){
        return this.ordena.ordena(elementos);
    }

}
