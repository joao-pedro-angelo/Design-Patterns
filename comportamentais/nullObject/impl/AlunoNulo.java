package comportamentais.nullObject.impl;

public class AlunoNulo extends Aluno{

    public AlunoNulo(){}

    @Override
    public Long getMatricula() {
        return -1L;
    }

    @Override
    public String getName() {
        return "Aluno não cadastrado";
    }
}
