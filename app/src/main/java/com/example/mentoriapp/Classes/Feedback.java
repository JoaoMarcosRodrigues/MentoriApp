package com.example.mentoriapp.Classes;

public class Feedback {
    private int id;
    private String emailMentor;
    private String descricao;

    public Feedback(int id, String emailMentor, String descricao) {
        this.id = id;
        this.emailMentor = emailMentor;
        this.descricao = descricao;
    }

    public Feedback() {}

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
