package comportamentais.templateMethod.impl;

public abstract class SuperClasse {

    public String hoodMethod(){
        return this.metodoAux() + "Template Method em ação!";
    }

    protected abstract String metodoAux();
}
