import java.util.Collections;
import java.util.List;

public class InsertionSort implements Strategy{
    
    @Override
    public List<Integer> ordena(List<Integer> elementos) {
        Collections.sort(elementos);
        return elementos;
    }

}
