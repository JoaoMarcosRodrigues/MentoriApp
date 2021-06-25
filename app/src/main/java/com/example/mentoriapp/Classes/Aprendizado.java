package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Aprendizado implements Parcelable {
    private int id;
    String tituloRelato;
    String tituloAprendizado;
    String descricaoAprendizado;
    String emailMentorado;

    public Aprendizado() { }

    public Aprendizado(int id, String tituloRelato, String tituloAprendizado, String descricaoAprendizado, String emailMentorado) {
        this.id = id;
        this.tituloRelato = tituloRelato;
        this.tituloAprendizado = tituloAprendizado;
        this.descricaoAprendizado = descricaoAprendizado;
        this.emailMentorado = emailMentorado;
    }

    protected Aprendizado(Parcel in) {
        id = in.readInt();
        tituloRelato = in.readString();
        tituloAprendizado = in.readString();
        descricaoAprendizado = in.readString();
        emailMentorado = in.readString();
    }

    public static final Creator<Aprendizado> CREATOR = new Creator<Aprendizado>() {
        @Override
        public Aprendizado createFromParcel(Parcel in) {
            return new Aprendizado(in);
        }

        @Override
        public Aprendizado[] newArray(int size) {
            return new Aprendizado[size];
        }
    };

    public String getEmailMentorado() {
        return emailMentorado;
    }

    public void setEmailMentorado(String emailMentorado) {
        this.emailMentorado = emailMentorado;
    }

    public String getTituloAprendizado() {
        return tituloAprendizado;
    }

    public void setTituloAprendizado(String tituloAprendizado) {
        this.tituloAprendizado = tituloAprendizado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloRelato() {
        return tituloRelato;
    }

    public void setTituloRelato(String tituloRelato) {
        this.tituloRelato = tituloRelato;
    }

    public String getDescricaoAprendizado() {
        return descricaoAprendizado;
    }

    public void setDescricaoAprendizado(String descricaoAprendizado) {
        this.descricaoAprendizado = descricaoAprendizado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tituloRelato);
        dest.writeString(tituloAprendizado);
        dest.writeString(descricaoAprendizado);
        dest.writeString(emailMentorado);
    }
}
