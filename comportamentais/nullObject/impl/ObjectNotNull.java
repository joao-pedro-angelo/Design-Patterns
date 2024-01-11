package comportamentais.nullObject.impl;

public class ObjectNotNull {

    private String name;

    public ObjectNotNull(){}

    public ObjectNotNull(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return "Eu tenho valor associado, não sou null. Meu nome é: " + this.name;
    }
}
