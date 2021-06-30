package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Avaliacao implements Parcelable {
    private int id;
    String tituloRelato;
    String tituloAvaliacao;
    String descricaoAvaliacao;
    String emailMentor;

    public Avaliacao(int id, String tituloRelato, String tituloAvaliacao, String descricaoAvaliacao, String emailMentor) {
        this.id = id;
        this.tituloRelato = tituloRelato;
        this.tituloAvaliacao = tituloAvaliacao;
        this.descricaoAvaliacao = descricaoAvaliacao;
        this.emailMentor = emailMentor;
    }

    public Avaliacao() {
    }

    protected Avaliacao(Parcel in) {
        id = in.readInt();
        tituloRelato = in.readString();
        tituloAvaliacao = in.readString();
        descricaoAvaliacao = in.readString();
        emailMentor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tituloRelato);
        dest.writeString(tituloAvaliacao);
        dest.writeString(descricaoAvaliacao);
        dest.writeString(emailMentor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Avaliacao> CREATOR = new Creator<Avaliacao>() {
        @Override
        public Avaliacao createFromParcel(Parcel in) {
            return new Avaliacao(in);
        }

        @Override
        public Avaliacao[] newArray(int size) {
            return new Avaliacao[size];
        }
    };

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

    public String getTituloAvaliacao() {
        return tituloAvaliacao;
    }

    public void setTituloAvaliacao(String tituloAvaliacao) {
        this.tituloAvaliacao = tituloAvaliacao;
    }

    public String getDescricaoAvaliacao() {
        return descricaoAvaliacao;
    }

    public void setDescricaoAvaliacao(String descricaoAvaliacao) {
        this.descricaoAvaliacao = descricaoAvaliacao;
    }

    public String getEmailMentor() {
        return emailMentor;
    }

    public void setEmailMentor(String emailMentor) {
        this.emailMentor = emailMentor;
    }
}
