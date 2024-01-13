package comportamentais.nullObject.impl;

public class Aluno {

    private Long matricula;
    private String name;

    public Aluno(){

    }

    public Aluno(Long matricula, String name) {
        this.matricula = matricula;
        this.name = name;
    }

    public Long getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
