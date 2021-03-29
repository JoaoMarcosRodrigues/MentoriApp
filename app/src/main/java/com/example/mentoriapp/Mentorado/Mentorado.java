package com.example.mentoriapp.Mentorado;

public class Mentorado {
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
}
