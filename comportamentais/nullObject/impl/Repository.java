package comportamentais.nullObject.impl;

import java.util.HashMap;
import java.util.Map;

public class Repository {

    private Map<Integer, ObjectNotNull> objects;

    public Repository(){
        this.objects = new HashMap<>();
    }

    public ObjectNotNull getObject(Integer key){
        if (this.objects.containsKey(key)) return this.objects.get(key);
        else return new ObjectNull("Sou nulo");
    }
}
