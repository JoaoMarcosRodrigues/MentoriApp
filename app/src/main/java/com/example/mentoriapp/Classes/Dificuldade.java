package com.example.mentoriapp.Classes;

public class Dificuldade {
    private int id;
    private String tituloRelato;
    private String tagDificuldade;
    private String descricaoDificuldade;
    private boolean favorito;
    private String emailMentorado;

    public Dificuldade(int id, String tituloRelato, String tagDificuldade, String descricaoDificuldade, boolean favorito, String emailMentorado) {
        this.id = id;
        this.tituloRelato = tituloRelato;
        this.tagDificuldade = tagDificuldade;
        this.descricaoDificuldade = descricaoDificuldade;
        this.favorito = favorito;
        this.emailMentorado = emailMentorado;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getEmailMentorado() {
        return emailMentorado;
    }

    public void setEmailMentorado(String emailMentorado) {
        this.emailMentorado = emailMentorado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dificuldade(){}

    public String getTituloRelato() {
        return tituloRelato;
    }

    public void setTituloRelato(String tituloRelato) {
        this.tituloRelato = tituloRelato;
    }

    public String getTagDificuldade() {
        return tagDificuldade;
    }

    public void setTagDificuldade(String tagDificuldade) {
        this.tagDificuldade = tagDificuldade;
    }

    public String getDescricaoDificuldade() {
        return descricaoDificuldade;
    }

    public void setDescricaoDificuldade(String descricaoDificuldade) {
        this.descricaoDificuldade = descricaoDificuldade;
    }
}
