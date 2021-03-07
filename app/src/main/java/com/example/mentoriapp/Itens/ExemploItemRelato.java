package com.example.mentoriapp.Itens;

public class ExemploItemRelato {
    private String mTituloRelato;
    private String mTemaRelato;
    private String mDataRelato;

    public ExemploItemRelato(String tituloRelato, String temaRelato, String dataRelato){
        mTituloRelato = tituloRelato;
        mTemaRelato = temaRelato;
        mDataRelato = dataRelato;
    }

    public String getTituloRelato(){
        return mTituloRelato;
    }

    public String getTemaRelato(){
        return mTemaRelato;
    }

    public String getDataRelato(){
        return mDataRelato;
    }


}
