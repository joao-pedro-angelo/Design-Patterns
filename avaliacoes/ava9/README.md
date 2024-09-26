# Avaliação 9 - Command

Com base no padrão Command, implemente um sistema de edição de texto que permita ao usuário adicionar e remover parágrafos.
Além disso, o sistema deve permitir a impressão do conteúdo do documento e a reversão da última operação executada (undo).
A operação de impressão não é reversível. Segue um exemplo do funcionamento:

Adicionar parágrafo “Bom dia pessoal”

Adicionar parágrafo “Boa atividade!”

Imprimir conteúdo
> "Bom dia pessoal"
> "Boa atividade!"

Reverter operação

Imprimir conteúdo
> "Bom dia pessoal"

Adicionar parágrafo “Boa sorte!!!”

Imprimir conteúdo
> "Bom dia pessoal"
> "Boa sorte!!!"

Reverter operação

Reverter operação

Imprimir conteúdo
> ""
