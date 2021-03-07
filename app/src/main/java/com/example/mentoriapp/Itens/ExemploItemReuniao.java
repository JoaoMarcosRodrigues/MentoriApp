package com.example.mentoriapp.Itens;

public class ExemploItemReuniao {
    private int mImageResourceReuniao;
    private String mTituloReuniao;
    private String mDescricaoReuniao;

    public ExemploItemReuniao(int imageResourceReuniao, String tituloReuniao, String descricaoReuniao){
        mImageResourceReuniao = imageResourceReuniao;
        mTituloReuniao = tituloReuniao;
        mDescricaoReuniao = descricaoReuniao;
    }

    public int getImageResourceReuniao(){
        return mImageResourceReuniao;
    }

    public String getTituloReuniao(){
        return mTituloReuniao;
    }

    public String getDescricaoReuniao(){
        return mDescricaoReuniao;
    }


}
