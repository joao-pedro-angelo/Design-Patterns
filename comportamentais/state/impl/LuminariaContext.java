package comportamentais.state.impl;

// Contexto
public class LuminariaContext {
    private State estado;

    public void setEstado(State estado) {
        this.estado = estado;
    }

    public void ligar() {
        estado.ligar(this);
    }

    public void desligar() {
        estado.desligar(this);
    }

    public void modoEconomia() {
        estado.modoEconomia(this);
    }
}

