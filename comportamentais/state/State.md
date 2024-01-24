# State

> Mudança de comportamento tendo como base o estado interno de um objeto

> Veja a implementação: [State IMPL](/comportamentais/state/impl)


---
## Contexto

É bastante comum que determinada entidade mude seu estado durante a sua execução.
Em um jogo, por exemplo, um personagem pode estar saudável, doente, cansado, dormindo...

A implementação trivial, nesses casos, é criar uma série de condicionais em diversos
pontos da classe. 

O padrão State é uma forma de lidar com essas mudanças de estado, sem que diversas condicionais precisem
ser criadas.


---
## Estrutura do Padrão State

O padrão State utiliza composição para permitir essa variação de comportamento de acordo como o estado de uma
entidade do sistema. Nesse padrão, os estados são representados por meio de classes que obedecem uma abstração comum,
podendo ser uma interface ou classe abstrata.

Desse modo, a entidade é composta por um objeto do tipo desta abstração (normalmente interface),
e nos métodos de negócio, delega para este objeto que o compõe a tarefa de execução do mesmo.

![img06](/imagens/img06.png)

Apenas a lógica comum será mantida na entidade e o comportamento específico
de cada estado estará definido em cada subclasse. Quando o estado for alterado, basta trocar
a instância utilizada para caracterizar o estado, que consequentemente o comportamento da entidade será alterado.


---
## Comparação com Strategy

Esse padrão é semelhante ao Strategy. Porém, há uma diferença na hora de invocar o método do objeto
que compõe a classe principal. Na hora da invocação, é necessário passar o objeto da classe principal como
argumento, para que o objeto invocado possa alterar o Estado da classe principal.

Exemplo de invocação:

```java
public class ClassePrincipal{
    
    private State state;
    
    public void executaState(){
        this.state.executa(this);
    }
}
```

O Strategy faz os objetos (Contexto e Estratégias) serem completamente
independentes entre si. Contudo, o State não restringe dependências entre estados concretos,
permitindo que eles alterem o contexto à vontade. Ou seja, os estados podem alterar o contexto.


---
## Implementação do State


- Decida qual classe vai ser o contexto. Pode ser uma classe existente que tenha código dependente de um estado
- Declare a interface do estado. Mire apenas em métodos que sejam comuns a todos os estados
- Para cada estado real, crie uma classe que implemente a interface
- Você pode perceber que o estado depende de dados do Contexto... Você pode contornar isso por meio de métodos acessadores públicos
- Na classe Contexto, adicione um campo de referência do tipo da interface do estado e um setter público para aquele atributo
- Para trocar o estado do contexto, crie uma instância do estado desejado e passe para o contexto. O ideal é que isso seja feito no Cliente ou no próprio Estado. Isso gera uma dependência entre a classe que criou e o Estado criado
