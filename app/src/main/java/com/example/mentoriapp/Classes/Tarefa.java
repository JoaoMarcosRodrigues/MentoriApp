package com.example.mentoriapp.Classes;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private String emailMentor;

    public Tarefa(int id, String titulo, String descricao, String emailMentor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.emailMentor = emailMentor;
    }

    public Tarefa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmailMentor() {
        return emailMentor;
    }

    public void setEmailMentor(String emailMentor) {
        this.emailMentor = emailMentor;
    }
}
