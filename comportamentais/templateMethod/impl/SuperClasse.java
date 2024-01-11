package comportamentais.templateMethod.impl;

public abstract class SuperClasse {

    public String hoodMethod(){
        return this.metodoAux();
    }

    abstract String metodoAux();
}
