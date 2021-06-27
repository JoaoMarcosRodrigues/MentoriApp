package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarefa implements Parcelable {
    private int id;
    private String titulo;
    private String descricao;
    private String emailAutor;
    private String emailDestinatario;
    private boolean status;

    public Tarefa(int id, String titulo, String descricao, String emailAutor, String emailDestinatario, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.emailAutor = emailAutor;
        this.emailDestinatario = emailDestinatario;
        this.status = status;
    }

    public Tarefa() {
    }

    protected Tarefa(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        descricao = in.readString();
        emailAutor = in.readString();
        emailDestinatario = in.readString();
        status = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(emailAutor);
        dest.writeString(emailDestinatario);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tarefa> CREATOR = new Creator<Tarefa>() {
        @Override
        public Tarefa createFromParcel(Parcel in) {
            return new Tarefa(in);
        }

        @Override
        public Tarefa[] newArray(int size) {
            return new Tarefa[size];
        }
    };

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

    public String getEmailAutor() {
        return emailAutor;
    }

    public void setEmailAutor(String emailAutor) {
        this.emailAutor = emailAutor;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
