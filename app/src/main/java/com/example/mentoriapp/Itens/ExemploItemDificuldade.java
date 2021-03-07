package com.example.mentoriapp.Itens;

public class ExemploItemDificuldade {
    private String mTituloDificuldade;
    private boolean mFavoritoDificuldade;

    public ExemploItemDificuldade(String tituloDificuldade, boolean favoritoDificuldade){
        mTituloDificuldade = tituloDificuldade;
        mFavoritoDificuldade = favoritoDificuldade;
    }

    public String getTituloDificuldade(){
        return mTituloDificuldade;
    }

    public boolean getFavoritoDificuldade(){
        return mFavoritoDificuldade;
    }


}
