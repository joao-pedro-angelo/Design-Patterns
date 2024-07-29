public class Main {
    public static void main(String[] args){
        Funcionario dev = new Desenvolvedor("Nome do Dev");
        Funcionario gerente = new Gerente("Nome do Gerente");
        Funcionario lider = new Lider("Nome do Líder");

        Sprint sprint = new Sprint(gerente, lider);
        sprint.addDesenvolvedor(dev);
        sprint.addDesenvolvedor(gerente);

        System.out.println(sprint);
    }
}
