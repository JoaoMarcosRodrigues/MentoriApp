package com.example.mentoriapp.Itens;

public class ExemploItemDuvida {
    private String mTituloDuvida;
    private boolean mFavoritoDuvida;

    public ExemploItemDuvida(String tituloDuvida, boolean favoritoDuvida){
        mTituloDuvida = tituloDuvida;
        mFavoritoDuvida = favoritoDuvida;
    }

    public String getTituloDuvida(){
        return mTituloDuvida;
    }

    public boolean getFavoritoDuvida(){
        return mFavoritoDuvida;
    }


}
