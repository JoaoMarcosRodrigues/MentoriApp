package com.example.mentoriapp.Itens;

public class ExemploItemAprendizado {
    private String mTituloAprendizado;
    private String mDescricaoAprendizado;

    public ExemploItemAprendizado(String tituloAprendizado, String descricaoAprendizado){
        mTituloAprendizado = tituloAprendizado;
        mDescricaoAprendizado = descricaoAprendizado;
    }

    public String getTituloAprendizado(){
        return mTituloAprendizado;
    }

    public String getDescricaoAprendizado(){
        return mDescricaoAprendizado;
    }


}
