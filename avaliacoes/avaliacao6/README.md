# Padrão State - Avaliação 6

Na área de engenharia de software é comum o uso de metodologias de desenvolvimento ágeis como o Scrum.
Projetos Scrum fazem uso de um Scrum Board (SB) para organizar o andamento das User Stories (USs). 
O Scrum possui dois papéis principais que podem ser assumidos pelo pessoal do time: Desenvolvedores e o Scrum Master (SM), que atua como um gerente do processo.
No SB (imagem abaixo), cada cartão (post-it) corresponde a uma US que pode estar em um dos diferentes estados: ToDo, InProgress, ToVerify e Done (ver imagem em anexo). 

No SB, sempre que uma US é movida por um integrante do time ela muda de estado no processo de desenvolvimento, seguindo os seguintes critérios: 

- Quando uma US é criada ela inicia no estado ToDo;
- No estado ToDo, se uma US é movida ela passa para o estado InProgress. 
- Esse movimento pode ser realizado por qualquer integrante do time. 
- No estado InProgress, se uma US é movida ela passa para o estado ToVerify. 
- Apenas desenvolvedores podem realizar essa mudança de estado. Nada acontece caso o SM realize essa mudança. 
- No estado ToVerify, se uma US é movida e a mesma está aprovada ela passa para o estado Done. Caso não esteja aprovada ela passa para o estado ToDo. 
- Apenas o SM pode realizar essa mudança de estado. Nada acontece caso um desenvolvedor realize essa mudança. 
- No estado Done, se uma US é movida nada acontece.  

Com base no padrão State, implemente um sistema de Scrum Board em que USs podem ser movidas para diferentes estados com base nos critérios acima.
