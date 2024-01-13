package comportamentais.strategy.impl;

public class Main {
    public static void main(String[] args){
        CalculaRota calculaRota = new CalculaRotaCaminhada();
        GPS gps = new GPS(calculaRota, "Casa");

        System.out.println(gps.calculaRota());
    }
}
