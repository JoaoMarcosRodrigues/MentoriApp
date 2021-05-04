package com.example.mentoriapp.Classes;

public class Feedback {
    private int id;
    private String emailMentor;
    private String titulo;
    private String descricao;
    private String data;

    public Feedback(int id, String emailMentor, String titulo, String descricao, String data) {
        this.id = id;
        this.emailMentor = emailMentor;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    public Feedback() {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
