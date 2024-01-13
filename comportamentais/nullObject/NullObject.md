# Null Object

> O padrão de projeto "Null Object" é usado para evitar que métodos retornem null.<br>
> A ideia gerar é criar uma classe que herde a classe do objeto não-nulo. Em seguida, sobrescreva os métodos getters,
> retornando um valor padrão.

> Esse padrão é um bom exemplo de uso de herança.
> <br> A herança tem que ser usada com muita cautela. Quase sempre é melhor usar composição.
> <br> Porém, no caso do Null Object, ele é bem implementado com herança.

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

> Exemplo de implementação do padrão: [NullObject IMPL](/comportamentais/nullObject/impl)


---
## Conclusão

Observe que este é um padrão simples de ser implementado, mas muito importante.