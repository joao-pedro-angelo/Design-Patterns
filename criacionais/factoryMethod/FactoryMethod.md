# Factory Method

> Padrão de Projeto criacional<br>
> Separa a lógica de negócio da lógica de criação das dependências<br>
> Utiliza um Hook Method para delegar a criação da dependência para a subclasse


---
## Estrutura do padrão

O padrão Factory Method utiliza um hook method para delegar a criação
da instância para a subclasse. Isso permite que métodos mais gerais na superclasse possam
utilizar a instância mesmo sem conhecer a classe que implementa esta instância. Isto pode ser feito
invocando um método de criação que é implementado na subclasse.

![estrutura-factory](/imagens/img05.png)

A classe principal depende de uma entidade, mas ela não define e nem implementa 
a instância da dependência. Ela utiliza um método abstrato que é implementado na subclasse e essa sim, define e instancia
a dependência. 

A partir desse padrão é possível desacoplar a superclasse da criação de uma dependência.
Apenas as subclasses ficam acoplados as implementações das dependências.

Caso uma nova instância precise ser utilizada, basta criar uma subclasse que herde a classe principal e implemente o método abstrato.

> Muito importante ver a implementação de exemplo:<br>
> [Factory Method IMPL](/criacionais/factoryMethod/impl)