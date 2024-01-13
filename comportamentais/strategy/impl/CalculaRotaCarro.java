package comportamentais.strategy.impl;

public class CalculaRotaCarro implements CalculaRota{

    @Override
    public String calculaRota(String localizacaoAtual) {
        return "Com base na sua localização atual, siga em frente na rodovia " +
                "e vire na primeira curva à esquerda";
    }
}
