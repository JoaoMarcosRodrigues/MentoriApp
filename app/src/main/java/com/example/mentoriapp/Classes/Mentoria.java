package com.example.mentoriapp.Classes;

public class Mentoria {
    private int id;
    private String emailMentor;
    private String emailMentorado;
    private String nomeMentor;
    private String nomeMentorado;

    public Mentoria(int id, String emailMentor, String emailMentorado,String nomeMentor,String nomeMentorado) {
        this.id = id;
        this.emailMentor = emailMentor;
        this.emailMentorado = emailMentorado;
        this.nomeMentor = nomeMentor;
        this.nomeMentorado = nomeMentorado;
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

    public String getNomeMentor() {
        return nomeMentor;
    }

    public void setNomeMentor(String nomeMentor) {
        this.nomeMentor = nomeMentor;
    }

    public String getNomeMentorado() {
        return nomeMentorado;
    }

    public void setNomeMentorado(String nomeMentorado) {
        this.nomeMentorado = nomeMentorado;
    }
}
