package com.example.mentoriapp.Itens;

public class ExemploItemMeuMentorado {
    private int mImageMeuMentorado;
    private String mNomeMentorado;
    private String mAreaAtuacaoMentorado;

    public ExemploItemMeuMentorado(int imgMentorado, String nomeMentorado, String areaAtuacaoMentorado){
        mImageMeuMentorado = imgMentorado;
        mNomeMentorado = nomeMentorado;
        mAreaAtuacaoMentorado = areaAtuacaoMentorado;
    }

    public int getImagemMeuMentorado(){
        return mImageMeuMentorado;
    }

    public String getNomeMentorado(){
        return mNomeMentorado;
    }

    public String getAreaAtuacaoMentorado(){
        return mAreaAtuacaoMentorado;
    }


}
