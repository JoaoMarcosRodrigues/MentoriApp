package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Mentor implements Parcelable {
    private String uuid;
    private String email;
    private String areaAtuacao;
    private String senha;
    private String nome;
    private String telefone;
    private String profileUrl;

    public Mentor(String uuid, String email, String areaAtuacao,String senha, String nome, String telefone, String profileUrl) {
        this.uuid = uuid;
        this.email = email;
        this.areaAtuacao = areaAtuacao;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.profileUrl = profileUrl;
    }

    public Mentor() {}

    protected Mentor(Parcel in) {
        uuid = in.readString();
        email = in.readString();
        areaAtuacao = in.readString();
        senha = in.readString();
        nome = in.readString();
        telefone = in.readString();
        profileUrl = in.readString();
    }

    public static final Creator<Mentor> CREATOR = new Creator<Mentor>() {
        @Override
        public Mentor createFromParcel(Parcel in) {
            return new Mentor(in);
        }

        @Override
        public Mentor[] newArray(int size) {
            return new Mentor[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(nome);
        dest.writeString(telefone);
        dest.writeString(profileUrl);
    }
}
