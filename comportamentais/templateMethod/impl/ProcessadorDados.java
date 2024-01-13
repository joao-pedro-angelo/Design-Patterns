package comportamentais.templateMethod.impl;

public abstract class ProcessadorDados {

    public String processaDados(){
        String result = "";
        result += this.etapa01(result);
        result += "\n";
        result += this.etapa02(result);
        return result;
    }

    protected abstract String etapa01(String data);

    protected String etapa02(String data){
        return "";
    }
}
