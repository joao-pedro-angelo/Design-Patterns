package main.psoft;

public class Reserva {
    private String tituloLivro;
    private String emailUser;

    public Reserva(String tituloLivro, String emailUser){
        this.tituloLivro = tituloLivro;
        this.emailUser = emailUser;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
 
    
    
}
