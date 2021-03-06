package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Mentorado implements Parcelable {
    private String uuid;
    private String email;
    private String senha;
    private String nome;
    private String areaAtuacao;
    private String telefone;
    private String profileUrl;
    private String emailMentor;
    private int tipoUsuario;

    public Mentorado(){

    }

    public Mentorado(String uuid, String email, String emailMentor, String senha, String nome, String areaAtuacao, String telefone, String profileUrl, int tipo) {
        this.uuid = uuid;
        this.email = email;
        this.emailMentor = emailMentor;
        this.senha = senha;
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
        this.telefone = telefone;
        this.profileUrl = profileUrl;
        this.tipoUsuario = tipo;
    }

    protected Mentorado(Parcel in) {
        uuid = in.readString();
        nome = in.readString();
        email = in.readString();
        emailMentor = in.readString();
        senha = in.readString();
        areaAtuacao = in.readString();
        telefone = in.readString();
        profileUrl = in.readString();
        tipoUsuario = in.readInt();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

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

    public String getEmailMentor() {
        return emailMentor;
    }

    public void setEmailMentor(String emailMentor) {
        this.emailMentor = emailMentor;
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
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeInt(tipoUsuario);
        dest.writeString(emailMentor);
    }
}
