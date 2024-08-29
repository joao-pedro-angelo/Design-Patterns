import java.util.ArrayList;
import java.util.List;

public class Atividade {

    public static void main(String[] args) {

        Cliente cliente = new Cliente();
        List<Integer> elementos = new ArrayList<>();
        elementos.add(10);
        elementos.add(0);
        elementos.add(3);

        cliente.ordena(elementos);
        for (Integer e : elementos){
            System.out.println(e);
        }
    }
}
