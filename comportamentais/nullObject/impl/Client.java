package comportamentais.nullObject.impl;

public class Client {

    public static void main(String[] args){
        Repository repository = new Repository();
        ObjectNotNull result = repository.getObject(0);

        System.out.println(result.toString());
    }

}
