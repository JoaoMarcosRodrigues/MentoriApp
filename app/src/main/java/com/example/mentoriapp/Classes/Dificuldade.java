package com.example.mentoriapp.Classes;

public class Dificuldade {
    private int id;
    private int idRelato;
    private String tagDificuldade;
    private String descricaoDificuldade;

    public Dificuldade(int id, int idRelato, String tagDificuldade, String descricaoDificuldade) {
        this.id = id;
        this.idRelato = idRelato;
        this.tagDificuldade = tagDificuldade;
        this.descricaoDificuldade = descricaoDificuldade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dificuldade(){}

    public int getIdRelato() {
        return idRelato;
    }

    public void setIdRelato(int idRelato) {
        this.idRelato = idRelato;
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
