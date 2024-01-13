package comportamentais.strategy.impl;

public class GPS {

    private CalculaRota gps;
    private String localizacaoAtual;

    public GPS(CalculaRota gps, String localizacaoAtual){
        this.gps = gps;
        this.localizacaoAtual = localizacaoAtual;
    }

    public void setGps(CalculaRota gps){
        this.gps = gps;
    }

    public String calculaRota(){
        return this.gps.calculaRota(this.localizacaoAtual);
    }
}
