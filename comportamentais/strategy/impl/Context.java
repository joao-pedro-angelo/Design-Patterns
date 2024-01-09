package comportamentais.strategy.impl;

public class Context {

    private Strategy strategy;

    public Context(){
        this.strategy = null;
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public double calculaValor(Long tempo){
        return this.strategy.calculaValor(tempo);
    }
}
