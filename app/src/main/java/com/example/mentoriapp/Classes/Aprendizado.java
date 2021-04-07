package com.example.mentoriapp.Classes;

public class Aprendizado {
    private int id;
    int idRelato;
    String descricaoAprendizado;

    public Aprendizado() { }

    public Aprendizado(int id, int idRelato, String descricaoAprendizado) {
        this.id = id;
        this.idRelato = idRelato;
        this.descricaoAprendizado = descricaoAprendizado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRelato() {
        return idRelato;
    }

    public void setIdRelato(int idRelato) {
        this.idRelato = idRelato;
    }

    public String getDescricaoAprendizado() {
        return descricaoAprendizado;
    }

    public void setDescricaoAprendizado(String descricaoAprendizado) {
        this.descricaoAprendizado = descricaoAprendizado;
    }
}
