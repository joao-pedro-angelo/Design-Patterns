# Strategy

> Veja a modelagem deste padrão: [Modelagem - Strategy](/imagens/img02.png)

> Classe Contexto: classe principal que possue uma referência para uma interface

> Interface Estratégia - Strategy: define o que é comum a todas as estratégias de implementação

> Estratégias concretas: implementam a interface.

> Cliente: escolhe qual estratégia concreta irá compor o contexto.

---
## Problema - Cálculo do valor do estacionamento

Considere o sistema de um estacionamento que precisa utilizar
diversos critérios para calcular o valor que deve ser cobrado de seus clientes.

Os critérios mudam bastante. Para um veículo de passeio, tem uma regra.
Para um caminhão, outra. Para uma van, outros critérios. Dependendo do tempo em que 
o veículo irá passar no estacionamento, o critério também pode mudar.

Como implementar uma solução para este sistema?

---
## Primeira solução
### Método único e completo

```java
public class ContaEstacionamento{
    
    private Veiculo veiculo;
    private long horarioChegada;
    private long horarioSaida;
    
    public double valorConta(){
        if (veiculo instanceof Passeio){
            //logica aqui
        } else if (veiculo instanceof Caminhao){
            //logica aqui
        } else if (veiculo instanceof Van){
            //logica aqui
        }
    }
}
```

Observe que o código acima possui diversas condicionais.
Não é uma boa prática ter um método tão complexo quanto o mostrado acima.


---
## Segunda solução
### Herança

```java
public class ContaEstacionamento{
    
    private Veiculo veiculo;
    private Long horarioChegada;
    private Long horarioSaida;
    
    public abstract int calculaValor(){}
}
```

```java
public class ContaVeiculoPasseio extends ContaEstacionamento{
    
    public int calculaValor(){
        //logica aqui
    }
}
```

Com o uso da herança, temos uma superclasse que define um método abstrato.
Cada uma das sub-classes irá implementar o método de acordo com a regra de negócio que escolher.

Um dos problemas desta solução é a explosão de sub-classes.

Porém, o principal problema é que, após instanciar um objeto, por exemplo ContaVeiculoPasseio,
só será possível mudar a regra de negócio instanciando um outro objeto, de outra sub-classe.

Para um problema como esse, é interessante ter uma solução que possibilite a flexibilidade na hora de moficar
a regra de negócio.


---
## Solução seguindo o padrão Strategy
### Composição

O desenvolvedor precisa de uma solução que permita que diferentes algoritmos
de cálculo de tarifa possam ser utilizados pela classe. Adicionalmente, é desejável que não haja duplicidade de código
e que o mesmo algoritmo de cálculo possa ser utilizados por diferentes empresas. Ademais, uma classe deve poder iniciar
a execução com um algoritmo e, em tempo de execução, poder mudar a escolha do algoritmo.

Uma solução que se encaixa nos requisitos acima, é a classe principal delegar a execução do algoritmo
para uma instância de uma classe que a compõe. Dessa forma, para trocar o comportamento do método,
basta trocar a instância que a compõe, por meio de um método setter.

![img01](/imagens/img01.png)

```java
public class ContaEstacionamento{
    
    private CalculoValor calculoValor;
    private Veiculo veiculo;
    private Long horarioChegada;
    private Long horarioSaida;
    
    public double valorConta(){
        return this.calculoValor.calculoEstacionamento(this.horarioChegada, this.horarioSaida, this.veiculo);
    }
    
    public void setCalculoValor(CalculoValor calculoValor){
        this.calculoValor = calculoValor;
    }
}
```

> CalculoValor é uma interface que define o método calculoValor().

> Outro exemplo de implementação: [StrategyIMPL](/comportamentais/strategy/impl)
