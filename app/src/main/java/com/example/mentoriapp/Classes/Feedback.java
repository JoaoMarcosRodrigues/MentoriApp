package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Feedback implements Parcelable {
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

    protected Feedback(Parcel in) {
        id = in.readInt();
        emailMentor = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        data = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(emailMentor);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };

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
