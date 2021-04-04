package com.example.mentoriapp.Relato;

public class Relato {
    private String tema;
    private String descricao;
    private String data;
    private Boolean presencial;
    private String tarefa_associada;

    public Relato(String tema, String descricao, String data, Boolean presencial, String tarefa_associada) {
        this.tema = tema;
        this.descricao = descricao;
        this.data = data;
        this.presencial = presencial;
        this.tarefa_associada = tarefa_associada;
    }

    public Relato() {}

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
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

    public Boolean getPresencial() {
        return presencial;
    }

    public void setPresencial(Boolean presencial) {
        this.presencial = presencial;
    }

    public String getTarefa_associada() {
        return tarefa_associada;
    }

    public void setTarefa_associada(String tarefa_associada) {
        this.tarefa_associada = tarefa_associada;
    }
}
