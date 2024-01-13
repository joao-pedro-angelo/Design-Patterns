package comportamentais.nullObject.impl;

import java.util.HashMap;
import java.util.Map;

public class RepositorioAlunos {

    private Map<Long, Aluno> alunos;

    public RepositorioAlunos(){
        this.alunos = new HashMap<>();
    }

    public Aluno getAluno(Long matricula){
        if (this.alunos.containsKey(matricula)){
            return this.alunos.get(matricula);
        } else return new AlunoNulo();
    }
}
