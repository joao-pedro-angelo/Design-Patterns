package comportamentais.state.impl;

public class DesligadaState implements State{
    @Override
    public void ligar(LuminariaContext luminaria) {
        System.out.println("Ligando a luminária...");
        LigadaState ligadaState = new LigadaState();
        luminaria.setEstado(ligadaState);
    }

    @Override
    public void desligar(LuminariaContext luminaria) {
        System.out.println("Luminária já está desligada.");
    }

    @Override
    public void modoEconomia(LuminariaContext luminaria) {
        System.out.println("Entrando em modo de economia... Modo de economia ativado!");
        EconomiaState economiaState = new EconomiaState();
        luminaria.setEstado(economiaState);
    }
}
