package comportamentais.state.impl;

public class LigadaState implements State{
    @Override
    public void ligar(LuminariaContext luminaria) {
        System.out.println("A luminária já está ligada.");
    }

    @Override
    public void desligar(LuminariaContext luminaria) {
        System.out.println("Desligando a luminária... Desligada!");
        DesligadaState desligadaState = new DesligadaState();
        luminaria.setEstado(desligadaState);
    }

    @Override
    public void modoEconomia(LuminariaContext luminaria) {
        System.out.println("Entrando em modo de economia... Modo de Economia ativado!");
        EconomiaState economiaState = new EconomiaState();
        luminaria.setEstado(economiaState);
    }
}
