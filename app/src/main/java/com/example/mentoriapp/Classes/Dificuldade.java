package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Dificuldade implements Parcelable {
    private int id;
    private String tituloRelato;
    private String tagDificuldade;
    private String descricaoDificuldade;
    private boolean favorito;
    private String emailMentorado;

    public Dificuldade(int id, String tituloRelato, String tagDificuldade, String descricaoDificuldade, boolean favorito, String emailMentorado) {
        this.id = id;
        this.tituloRelato = tituloRelato;
        this.tagDificuldade = tagDificuldade;
        this.descricaoDificuldade = descricaoDificuldade;
        this.favorito = favorito;
        this.emailMentorado = emailMentorado;
    }

    protected Dificuldade(Parcel in) {
        id = in.readInt();
        tituloRelato = in.readString();
        tagDificuldade = in.readString();
        descricaoDificuldade = in.readString();
        favorito = in.readByte() != 0;
        emailMentorado = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tituloRelato);
        dest.writeString(tagDificuldade);
        dest.writeString(descricaoDificuldade);
        dest.writeByte((byte) (favorito ? 1 : 0));
        dest.writeString(emailMentorado);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dificuldade> CREATOR = new Creator<Dificuldade>() {
        @Override
        public Dificuldade createFromParcel(Parcel in) {
            return new Dificuldade(in);
        }

        @Override
        public Dificuldade[] newArray(int size) {
            return new Dificuldade[size];
        }
    };

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getEmailMentorado() {
        return emailMentorado;
    }

    public void setEmailMentorado(String emailMentorado) {
        this.emailMentorado = emailMentorado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dificuldade(){}

    public String getTituloRelato() {
        return tituloRelato;
    }

    public void setTituloRelato(String tituloRelato) {
        this.tituloRelato = tituloRelato;
    }

    public String getTagDificuldade() {
        return tagDificuldade;
    }

    public void setTagDificuldade(String tagDificuldade) {
        this.tagDificuldade = tagDificuldade;
    }

    public String getDescricaoDificuldade() {
        return descricaoDificuldade;
    }

    public void setDescricaoDificuldade(String descricaoDificuldade) {
        this.descricaoDificuldade = descricaoDificuldade;
    }
}
