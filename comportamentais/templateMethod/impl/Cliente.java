package comportamentais.templateMethod.impl;

public class Cliente {

    public static void main(String[] args) {
        SuperClasse aux = new SubClasse01();
        System.out.println(aux.hoodMethod());
    }
}
