package com.example.mentoriapp.Classes;

public class Reuniao {
    private int id;
    private String descricao;
    private String data;
    private String horario;

    public Reuniao(int id, String descricao, String data, String horario) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.horario = horario;
    }

    public Reuniao() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
