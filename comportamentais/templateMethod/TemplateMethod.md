# Template Method

> Template Method é um modelo de algoritmo que possui algumas partes fixas e algumas partes variáveis. As partes variáveis são lacunas
que precisam ser completadas para que o algoritmo faça realmente sentido. As lacunas são representadas como hook methods que podem ser implementados nas sub-classes. Caso seja uma lacuna obrigatória, o método deve ser definido como abstrato
e caso a implementação seja opcional, o método pode ser concreto e normalmente
possui uma implementação vazia. O algoritmo é representado através de um método
na superclasse que coordena a execução dos hook methods.


---
## Hook Methods

> Métodos ganchos - Necessário uso de herança<br>
> A superclasse possui um método público que é invocado pelo cliente.
> Este método público invoca um método privado, que normalmente é definido como abstrato na superclasse e é
> implementado ou sobrescrito por uma sub-classe, que é quem de fato irá implementar o comportamente desejado.

A figura abaixo apresenta a estrutura do padrão Template Method. A
ClasseAbstrata representa a superclasse que implementa o TemplateMethod
e que define quais são os hook methods. A ClasseConcreta representa a classe
que herda o Template Method da ClasseAbstrata e define uma implementação concreta dos hook methods. A classe representada como Cliente invoca o
metodoTemplate(). Observe que apesar do tipo da variável ser do tipo da classe
abstrata, o tipo instanciado é o da subclasse que implementa os passos concretos do
algoritmo.

![Template Method](/imagens/img04.png)


---
## Template Method x Strategy

Ambos os padrões (Template e Strategy) dão o mesmo resultado.
Tanto um, quanto o outro são usados quando temos um método que pode ter
variações em seu comportamento, dependendo do objeto que o invoca.

Porém, o Strategy é mais flexível e adaptável, já que utiliza composição.

O template method só deve ser utilizado quando a herança for uma possibilidade válida de design.

O uso da herança gera um alto acoplamento entre a superclasse e a subclasse. Diferentemente do padrão Strategy, que as subclasses
não dependem de uma implementação concreta, mas sim de uma interface.
