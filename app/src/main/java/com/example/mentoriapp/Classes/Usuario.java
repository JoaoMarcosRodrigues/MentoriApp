package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String uuid;
    private String email;
    private String nome;
    private String telefone;
    private String photoUrl;
    private String areaAtuacao;
    private String senha;
    private int tipo;

    public Usuario(String uuid, String email, String nome, String telefone, String photoUrl, String areaAtuacao, String senha, int tipo){
        this.uuid = uuid;
        this.email = email;
        this.nome = nome;
        this.telefone = telefone;
        this.photoUrl = photoUrl;
        this.areaAtuacao = areaAtuacao;
        this.senha = senha;
        this.tipo = tipo;
    }

    protected Usuario(Parcel in) {
        uuid = in.readString();
        email = in.readString();
        nome = in.readString();
        telefone = in.readString();
        photoUrl = in.readString();
        areaAtuacao = in.readString();
        senha = in.readString();
        tipo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(email);
        dest.writeString(nome);
        dest.writeString(telefone);
        dest.writeString(photoUrl);
        dest.writeString(areaAtuacao);
        dest.writeString(senha);
        dest.writeInt(tipo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Usuario() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


}
