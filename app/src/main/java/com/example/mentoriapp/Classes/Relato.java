package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Relato implements Parcelable {
    private int id;
    private String titulo;
    private String tema;
    private String descricao;
    private String data;
    private String presencial;
    private String tarefa_associada;
    private String emailMentorado;

    public Relato(int id, String titulo, String tema, String descricao, String data, String presencial, String tarefa_associada, String emailMentorado) {
        this.id = id;
        this.titulo = titulo;
        this.tema = tema;
        this.descricao = descricao;
        this.data = data;
        this.presencial = presencial;
        this.tarefa_associada = tarefa_associada;
        this.emailMentorado = emailMentorado;
    }

    protected Relato(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        tema = in.readString();
        descricao = in.readString();
        data = in.readString();
        presencial = in.readString();
        tarefa_associada = in.readString();
        emailMentorado = in.readString();
    }

    public static final Creator<Relato> CREATOR = new Creator<Relato>() {
        @Override
        public Relato createFromParcel(Parcel in) {
            return new Relato(in);
        }

        @Override
        public Relato[] newArray(int size) {
            return new Relato[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmailMentorado() {
        return emailMentorado;
    }

    public void setEmailMentorado(String emailMentorado) {
        this.emailMentorado = emailMentorado;
    }

    public Relato() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPresencial() {
        return presencial;
    }

    public void setPresencial(String presencial) {
        this.presencial = presencial;
    }

    public String getTarefa_associada() {
        return tarefa_associada;
    }

    public void setTarefa_associada(String tarefa_associada) {
        this.tarefa_associada = tarefa_associada;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(tema);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(presencial);
        dest.writeString(tarefa_associada);
        dest.writeString(emailMentorado);
    }
}
