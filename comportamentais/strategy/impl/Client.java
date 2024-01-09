package comportamentais.strategy.impl;

public class Client {
    public static void main(String[] args){
        Context context = new Context();
        context.setStrategy(new StrategyConcret01());

        double result = context.calculaValor(10L);
        System.out.println(result);
    }
}
