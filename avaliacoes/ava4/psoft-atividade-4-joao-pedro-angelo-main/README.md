# Atividade PSoft Testes Automáticos e TDD

Para a atividade em questão você deve implementar por meio da metodologia de TDD (Test Driven Development) as user stories abaixo para um sistema de biblioteca. Para tal, você vai produzir duas versões da implementação e submeter ambas via formulário indicando os commits relacionados com cada versão:
1. Versão da implementação dos testes que não passam sem a implementação da lógica;
2. Versão da implementação dos testes que passam com a implementação da lógica. 

### US1
Como usuário do sistema desejo pesquisar livros com base no título. A busca deve considerar títulos parciais.

### US2
Como usuário do sistema desejo listar livros cadastrados no sistema. Por padrão a listagem deve considerar 
apenas livros disponíveis, mas também deve ser possível listar todos os livros cadastrados.

### US3
Como usuário do sistema desejo reservar livros disponíveis no sistema.
* Para a reserva o usuário deve informar o ID do livro e uma identificação do usuário;
* Apenas livros cadastrados e disponíveis podem ser reservados;
* Mensagens de erro devem ser exibidas indicando que o livro não pode ser reservado e o motivo.

**Observação:** Usem o JUnit 5 no classpath do projeto. 