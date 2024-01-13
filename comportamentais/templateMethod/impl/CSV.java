package comportamentais.templateMethod.impl;

public class CSV extends ProcessadorDados{

    @Override
    protected String etapa01(String data) {
        return "Eu processo CSVs";
    }

    @Override
    protected String etapa02(String data) {
        return "Essa sobrescrição é opcional!";
    }
}
