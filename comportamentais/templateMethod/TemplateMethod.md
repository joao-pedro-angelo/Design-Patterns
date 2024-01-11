# Template Method

> Padrão que utiliza herança e hook methods para definir um algoritmo geral que 
> pode ter variações no seu comportamento.


---
## Hook Methods

> Métodos ganchos - Necessário uso de herança<br>
> A superclasse possui um método público que é invocado pelo cliente.
> Este método público invoca um método privado, que normalmente é definido como abstrato na superclasse e é
> implementado ou sobrescrito por uma sub-classe, que é quem de fato irá implementar o comportamente desejado.


```java
public abstract class SuperClasse{
    
    public String metodoPrincipal(){
        return this.metodoHook();
    }
    
    //Hook method
    abstract String metodoHook();
}
```

```java
public class SubClasse01 extends SuperClasse{
    
    public String metodoHook(){
        return "SubClasse01";
    }
}
```

```java
public class SubClasse02 extends SuperClasse{
    
    public String metodoHook(){
        return "SubClasse02";
    }
}
```


---
## Estrutura do padrão

![img03](/imagens/img03.png)


---
## Template Method x Strategy

Ambos os padrões (Template e Strategy) dão o mesmo resultado.
Tanto um, quanto o outro são usados quando temos um método que pode ter
variações em seu comportamento, dependendo do objeto que o invoca.

Porém, o Strategy é mais flexível e adaptável, já que utiliza composição.

O template method só deve ser utilizado quando a herança for uma possibilidade válida de design.

O uso da herança gera um alto acoplamento entre a superclasse e a subclasse. Diferentemente do padrão Strategy, que as subclasses
não dependem de uma implementação concreta, mas sim de uma interface.
