# Design Patterns 

> Padrões de projetos: Forma de documentar uma solução para um problema de modelagem de software<br>
> São soluções que foram implementadas com sucesso de forma recorrente em diferentes contextos

> Revisão dos principais conceitos da orientação a objetos: [Revisão](/RevisaoPOO.md)


---
## Introdução

Conhecer os conceitos da orientação a objetos não é suficiente para desenvolver um software OO.<br>
É preciso saber quando usar cada um desses conceitos e como eles se relacionam entre si.<br>
Os padrões de projetos surgem como ferramentas que podem ser usadas neste processo, auxiliando o desenvolvedor a tomar
a melhor decisão na hora de modelar e desenvolver o software.


---
## Padrões abordados neste repositório

### [Strategy](/comportamentais/strategy/Strategy.md)

> Diversos algoritmos que podem ser usados de forma intercambiável.
> Com o strategy, a escolha de qual algoritmo usar pode ser feita em tempo de execução.

- Composição
- Interface que abstrái determinado comportamento (Strategy)
- Implementações dessa interface (implementações da estratégia)
- Classe principal - Contexto - que é composta pela interface


---
### [Template Method](/comportamentais/templateMethod/TemplateMethod.md)

> Padrão que utiliza hook methods<br>
> Definir um algoritmo geral que estabelece uma série de passos, que podem ser executados em diferentes ordens<br>

- Herança e Hook Methods
- Hook Methods: Especialização de comportamentos
- A superclasse pode definir uma base para um comportamento. A qual invoca um método que é apenas definido pela superclasse (método abstrato)
- Classe Cliente se comunicando diretamente com a classe abstrata que pode ter diferentes sub-classes
- Superclasse utilizando código implementado pela subclasse


---
### [Null Object](/comportamentais/nullObject/NullObject.md)

> Criação de uma entidade que herde de outra entidade. Os métodos devem ser sobrescritos
> de modo a representar um objeto nulo. Por exemplo, os métodos getters podem retornar um valor padrão,
> indicando que o objeto procurado não existe.

- Herança
- Extensão de uma classe pode ser a sua representação nula
- Código cliente consegue lidar com objetos que não existem de forma transparente, ou seja, sem necessidade de condicionais
- Representação de entidade que não existe


---
### [Factory Method](/criacionais/factoryMethod/FactoryMethod.md)

> Separação da lógica de negócios da criação das dependências<br>

- Herança e Hook Method
- Criação de uma classe abstrata, que define um método abstrato que retorna a dependência necessária
- A subclasse é quem define e implementa a dependência
- Superclasse utilizando código implementado pela subclasse


--- 