package comportamentais.state.impl;

public class EconomiaState implements State{
    @Override
    public void ligar(LuminariaContext luminaria) {
        System.out.println("Ligando a luminária em modo de economia");
    }

    @Override
    public void desligar(LuminariaContext luminaria) {
        System.out.println("Desligando...");
        DesligadaState desligadaState = new DesligadaState();
        luminaria.setEstado(desligadaState);
    }

    @Override
    public void modoEconomia(LuminariaContext luminaria) {
        System.out.println("Luminária já está em modo de economia.");
    }
}
