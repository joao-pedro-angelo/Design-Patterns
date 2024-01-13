package comportamentais.templateMethod.impl;

public class Main {
    public static void main(String[] args){
        ProcessadorDados dados01 = new PDF();
        ProcessadorDados dados02 = new CSV();

        System.out.println(dados01.processaDados());
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(dados02.processaDados());
    }
}
