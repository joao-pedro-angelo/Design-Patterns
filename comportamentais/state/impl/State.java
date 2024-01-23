package comportamentais.state.impl;

// Estado abstrato
public interface State {
    void ligar(LuminariaContext luminaria);
    void desligar(LuminariaContext luminaria);
    void modoEconomia(LuminariaContext luminaria);
}
