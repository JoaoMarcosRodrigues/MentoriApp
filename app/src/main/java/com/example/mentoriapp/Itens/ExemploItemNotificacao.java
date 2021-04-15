package com.example.mentoriapp.Itens;

public class ExemploItemNotificacao {
    private String mTituloNotificacao;
    private String mDescricaoNotificacao;

    public ExemploItemNotificacao(String tituloNotificacao, String descricaoNotificacao){
        mTituloNotificacao = tituloNotificacao;
        mDescricaoNotificacao = descricaoNotificacao;
    }

    public String getTituloAprendizado(){
        return mTituloNotificacao;
    }

    public String getDescricaoAprendizado(){
        return mDescricaoNotificacao;
    }


}
