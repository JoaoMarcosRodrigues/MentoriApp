package com.example.mentoriapp.Mentorado;

import android.os.Parcel;
import android.os.Parcelable;

public class Mentorado implements Parcelable {
    private String uuid;
    private String nome;
    private String areaAtuacao;
    private String telefone;
    private String profileUrl;

    public Mentorado(){

    }

    public Mentorado(String uuid, String nome, String areaAtuacao, String telefone, String profileUrl) {
        this.uuid = uuid;
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
        this.telefone = telefone;
        this.profileUrl = profileUrl;
    }

    protected Mentorado(Parcel in) {
        uuid = in.readString();
        nome = in.readString();
        areaAtuacao = in.readString();
        telefone = in.readString();
        profileUrl = in.readString();
    }

    public static final Creator<Mentorado> CREATOR = new Creator<Mentorado>() {
        @Override
        public Mentorado createFromParcel(Parcel in) {
            return new Mentorado(in);
        }

        @Override
        public Mentorado[] newArray(int size) {
            return new Mentorado[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
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
        dest.writeString(nome);
        dest.writeString(areaAtuacao);
        dest.writeString(telefone);
        dest.writeString(profileUrl);
    }
}
