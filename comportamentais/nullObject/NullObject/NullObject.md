# Null Object

> O padrão de projeto "Null Object" é usado para evitar que métodos retornem null.<br>
> A ideia gerar é criar uma classe que herde a classe do objeto não-nulo. Em seguida, sobrescreva os métodos getters,
> retornando um valor padrão.

---
## Contexto

Imagine que você implementou um metódo que irá recuperar determinada informaação.<br>
Por exemplo, o método recuperaAluno(Long matricula);<br>

Tal método pode acessar uma estrutura de dados, possivelmente um Mapa, que armazena os alunos e a chave é a matrícula.<br>
Caso não haja aluno com a matrícula informada, o método retorna null.

Implementar o método desta forma não é um erro, é bastante comum retornar null.<br>
Porém, tal prática faz com que o programador tenha que tratar esse valor null ao longo do código, seja utilizando condicionais
ou outras práticas.

Uma forma de evitar este processo é seguir o padrão<br> **Null Object**!


---
## Implementar o padrão

```java
import java.util.HashMap;
import java.util.Map;

public class ControleAcademico {

    private Map<Long, Aluno> alunos;

    public ControleAcademico() {
        this.alunos = new HashMap<>();
    }
    
    public String getAluno(Long matricula){
        if (this.alunos.containsKey(matriculo)){
            return this.alunos.get(matricula);
        } else return new AlunoNull();
    }
}
```

```java
public class Aluno{
    
    private Long matricula;
    
    public Aluno(){}
    
    public Aluno(Long matricula){
        this.matricula = matricula;
    }
    
    //getters and setters
}
```

```java
public class AlunoNulo extends Aluno{
    
    public AlunoNulo(){
        super(0L);
    }
    
    //getters and setter retornando o valor desejado para quando o Aluno não existir
}
```


---
## Conclusão

Observe que este é um padrão simples de ser implementado, mas muito importante.