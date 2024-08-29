# Avaliação 05 - Padrão Strategy

Algoritmos de ordenação têm como objetivo colocar elementos em uma sequência ordenada.
Existem pelo menos quatro formas bastante difundidas de ordenação: bubble sort, insertion sort, merge sort e quick sort.
Dependendo dos dados a serem ordenados é possível que um algoritmo diferente seja o mais eficiente para cada cenário.

Com base no padrão Strategy, implemente um sistema com uma classe cliente (contexto),
que pode alternar livremente a estratégia de ordenação usada para ordenar uma lista de inteiros
a partir da seguinte assinatura de método: 

> List<Integer> ordena(List<Integer> elementos); 

Observação: Não é necessário implementar os algoritmos de ordenação em si.
