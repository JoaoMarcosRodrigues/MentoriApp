package com.example.mentoriapp.Classes;

public class Avaliacao {
    private int id;
    int idRelato;
    String tituloAvaliacao;
    String descricaoAvaliacao;
    String emailMentor;

    public Avaliacao(int id, int idRelato, String tituloAvaliacao, String descricaoAvaliacao, String emailMentor) {
        this.id = id;
        this.idRelato = idRelato;
        this.tituloAvaliacao = tituloAvaliacao;
        this.descricaoAvaliacao = descricaoAvaliacao;
        this.emailMentor = emailMentor;
    }

    public Avaliacao() {
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

    public String getTituloAvaliacao() {
        return tituloAvaliacao;
    }

    public void setTituloAvaliacao(String tituloAvaliacao) {
        this.tituloAvaliacao = tituloAvaliacao;
    }

    public String getDescricaoAvaliacao() {
        return descricaoAvaliacao;
    }

    public void setDescricaoAvaliacao(String descricaoAvaliacao) {
        this.descricaoAvaliacao = descricaoAvaliacao;
    }

    public String getEmailMentor() {
        return emailMentor;
    }

    public void setEmailMentor(String emailMentor) {
        this.emailMentor = emailMentor;
    }
}
