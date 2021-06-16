package com.example.mentoriapp.Classes;

public class Aprendizado {
    private int id;
    String tituloRelato;
    String tituloAprendizado;
    String descricaoAprendizado;
    String emailMentorado;

    public Aprendizado() { }

    public Aprendizado(int id, String tituloRelato, String tituloAprendizado, String descricaoAprendizado, String emailMentorado) {
        this.id = id;
        this.tituloRelato = tituloRelato;
        this.tituloAprendizado = tituloAprendizado;
        this.descricaoAprendizado = descricaoAprendizado;
        this.emailMentorado = emailMentorado;
    }

    public String getEmailMentorado() {
        return emailMentorado;
    }

    public void setEmailMentorado(String emailMentorado) {
        this.emailMentorado = emailMentorado;
    }

    public String getTituloAprendizado() {
        return tituloAprendizado;
    }

    public void setTituloAprendizado(String tituloAprendizado) {
        this.tituloAprendizado = tituloAprendizado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloRelato() {
        return tituloRelato;
    }

    public void setTituloRelato(String tituloRelato) {
        this.tituloRelato = tituloRelato;
    }

    public String getDescricaoAprendizado() {
        return descricaoAprendizado;
    }

    public void setDescricaoAprendizado(String descricaoAprendizado) {
        this.descricaoAprendizado = descricaoAprendizado;
    }
}
