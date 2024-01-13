package comportamentais.nullObject.impl;

public class Main {

    public static void main(String[] args){
        RepositorioAlunos alunos = new RepositorioAlunos();
        Aluno aluno = alunos.getAluno(1298L);

        System.out.println("Aluno: " + aluno.getName());
        System.out.println("Matrícula: " + aluno.getMatricula());
    }

}
