package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Mentor implements Parcelable {
    private String uuid;
    private String email;
    private String areaAtuacao;
    private String formacao;
    private String curriculo;
    private String tempoAtuacao;
    private String senha;
    private String nome;
    private String telefone;
    private String profileUrl;
    private int tipoUsuario;

    public Mentor(String uuid, String email, String areaAtuacao, String formacao, String curriculo, String tempoAtuacao, String senha, String nome, String telefone, String profileUrl, int tipoUsuario) {
        this.uuid = uuid;
        this.email = email;
        this.areaAtuacao = areaAtuacao;
        this.formacao = formacao;
        this.curriculo = curriculo;
        this.tempoAtuacao = tempoAtuacao;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.profileUrl = profileUrl;
        this.tipoUsuario = tipoUsuario;
    }

    public Mentor() {
    }

    protected Mentor(Parcel in) {
        uuid = in.readString();
        email = in.readString();
        areaAtuacao = in.readString();
        formacao = in.readString();
        curriculo = in.readString();
        tempoAtuacao = in.readString();
        senha = in.readString();
        nome = in.readString();
        telefone = in.readString();
        profileUrl = in.readString();
        tipoUsuario = in.readInt();
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

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    public String getTempoAtuacao() {
        return tempoAtuacao;
    }

    public void setTempoAtuacao(String tempoAtuacao) {
        this.tempoAtuacao = tempoAtuacao;
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

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(email);
        dest.writeString(areaAtuacao);
        dest.writeString(formacao);
        dest.writeString(curriculo);
        dest.writeString(tempoAtuacao);
        dest.writeString(senha);
        dest.writeString(nome);
        dest.writeString(telefone);
        dest.writeString(profileUrl);
        dest.writeInt(tipoUsuario);
    }
}
