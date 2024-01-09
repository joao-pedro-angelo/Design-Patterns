# Revisão

## Tabela de conteúdos

- Histórico
- Classes e Objetos
- Herança
- Encapsulamento
- Interfaces e classes abstratas
- Polimorfismo
- Estes conceitos são suficientes ?


---
### Histórico

Em linguagens antigas, não havia nenhuma separação dos elementos de código. 
Para criar loops ou condicionais, era necessário utilizar o comando goto.
Isso tornava o código difícil de ser gerenciado.

Com a criação da programação estruturada, este problema foi parcialmente resolvido.
Comandos condicionais e de iteração agora facilitavam o entendimento do fluxo de execução de um programa.
Outra adição extremamente importante que surgiu neste paradigma, foram as funções.
A partir delas foi possível dividir a lógica do programa, permitindo sua modularização.
Isso facilita que essas funções possam ser reutilizadas em outras aplicações.

Como uma evolução da programação estruturada, surgiu a programação orientada a objetos. 


---
### Classes e Objetos

Na programação orientada a objetos, são definidos novos tipos a partir da criação de classes.
Esses novos tipos podem ser instanciados como objetos.
A ideia é que um objeto represente uma entidade concreta e a classe uma abstração.
É como se "Gato" fosse uma classe e "Garfield" fosse a instância desta classe.

A classe possui estados e comportamentos, que são representados pelos atributos e métodos.
Enquanto uma classe possui características, um objeto possui valores para aquelas características.

Projetar um sistema orientado a objetos significa definir quais classes serão criadas e
como elas irão interagir entre si.

Se suas classes não representam abstrações do sistema e são apenas repositórios de funções,
então você está programando seguindo o paradigma estruturado.


---
### Herança

> Diferentes níveis de abstração

A herança é uma característica do paradigma orientado a objetos,
que permite que abstrações possam ser definidas em diferentes níveis.
Há a super-classe e é possível ter sub-classes de determinada super-classe.

Um dos grandes desafios da orientação a objetos, é identificar quando a herança deve ser utilizada.


---
### Encapsulamento

> "A ignorância é uma bênção!"
> Imagine como seria se, para desfrutar do ar-condicionado, você precisasse compreender como ele funciona...

Antes da programação estruturada, o desenvolvedor precisava compreender os detalhes da implementação de cada parte do código.
Com a criação da funções, houve um avanço neste quesito. 

O encapsulamento é um conceito importantíssimo da orientação a objetos,
que diz que deve ter uma separação entre o comportamento interno de uma classe com a interface
pública que ela disponibiliza para uso. 

Este conceito possibilita o desacoplamento entre as classes.

Os métodos acessadores, como getters e setters, são bons exemplos de encapsulamento.
Para os usuários destes métodos, apenas uma informação está sendo recuperada (getter) ou modificada (setter),
porém, o método pode estar fazendo muito mais do que isso. 

O usuário não precisa conhecer os detalhes da implementação.


---
### Interfaces e classes abstratas

Tanto as interfaces quanto as classes abstratas, podem definir métodos abstratos que precisam ser implementados
por quem utilizar a interface ou a classe abstrata.
Porém, as classes abstratas podem possuir atributos e métodos concretos.

As interfaces são classes completamente abstratas, ou seja, não podem possuir métodos concretos e nem atributos.

Interface é um contrato que as classes podem assinar ao implementá-las. 

As interfaces possibilitam o desacoplamento entre as classes. 

> Programe para interfaces!

Seguir a norma acima indica que seu sistema está com baixo acoplamento. Pois as classes não precisam conhecer
detalhes da implementação de outras classes, apenas o que elas fazem, não como.

> O que fazem!


---
### Polimorfismo

> Qualquer objeto pode assumir a forma de uma de suas abstrações!

O polimorfismo é uma consequência da utilização de interfaces e de herança.
Um objeto pode ser atribuído ao tipo das suas abstrações.


---
### Estes conceitos são suficientes?

Não são!

Realizar a modelagem de um software é algo complexo.
Não basta conhecer os conceitos e nem saber como utilizá-los, é preciso compreender como eles se relacionam.

É preciso entender sobre o acoplamento entre classes, entre outros conhecimentos que os padrões de projeto podem fornecer.
