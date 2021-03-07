package com.example.mentoriapp.Itens;

public class ExemploItemTarefa {
    private String mTituloTarefa;
    private String mDescricaoTarefa;
    private boolean mStatusTarefa;
    private int mImgTarefa;

    public ExemploItemTarefa(int imgTarefa, String tituloTarefa, String descricaoTarefa, boolean statusTarefa){
        mTituloTarefa = tituloTarefa;
        mDescricaoTarefa = descricaoTarefa;
        mImgTarefa = imgTarefa;
        mStatusTarefa = statusTarefa;
    }

    public int getImgTarefa(){
        return mImgTarefa;
    }

    public String getTituloTarefa(){
        return mTituloTarefa;
    }

    public String getDescricaoTarefa(){
        return mDescricaoTarefa;
    }

    public boolean getStatusTarefa(){
        return mStatusTarefa;
    }


}
