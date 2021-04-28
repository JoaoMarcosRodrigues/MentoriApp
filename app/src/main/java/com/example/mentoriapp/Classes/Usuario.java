package com.example.mentoriapp.Classes;

public class Usuario {
    private String uuid;
    private String email;
    private String nome;
    private String telefone;
    private String photoUrl;
    private String areaAtuacao;
    private String senha;
    private int tipo;

    public Usuario(String uuid, String email, String nome, String telefone, String photoUrl, String areaAtuacao, String senha, int tipo) {
        this.uuid = uuid;
        this.email = email;
        this.nome = nome;
        this.telefone = telefone;
        this.photoUrl = photoUrl;
        this.areaAtuacao = areaAtuacao;
        this.senha = senha;
        this.tipo = tipo;
    }

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
