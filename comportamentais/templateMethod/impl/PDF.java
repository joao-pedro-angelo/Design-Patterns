package comportamentais.templateMethod.impl;

public class PDF extends ProcessadorDados{

    @Override
    protected String etapa01(String data) {
        return "Eu processo PDFs";
    }
}
