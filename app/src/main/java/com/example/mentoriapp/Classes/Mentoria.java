package com.example.mentoriapp.Classes;

public class Mentoria {
    private int id;
    private String emailMentor;
    private String emailMentorado;

    public Mentoria(int id, String emailMentor, String emailMentorado) {
        this.id = id;
        this.emailMentor = emailMentor;
        this.emailMentorado = emailMentorado;
    }

    public Mentoria() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailMentor() {
        return emailMentor;
    }

    public void setEmailMentor(String emailMentor) {
        this.emailMentor = emailMentor;
    }

    public String getEmailMentorado() {
        return emailMentorado;
    }

    public void setEmailMentorado(String emailMentorado) {
        this.emailMentorado = emailMentorado;
    }
}
