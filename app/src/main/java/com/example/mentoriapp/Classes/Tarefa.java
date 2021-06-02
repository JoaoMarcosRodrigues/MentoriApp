package com.example.mentoriapp.Classes;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private String email;
    private boolean status;

    public Tarefa(int id, String titulo, String descricao, String email, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.email = email;
        this.status = status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
