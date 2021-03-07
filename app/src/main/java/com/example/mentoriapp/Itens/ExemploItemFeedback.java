package com.example.mentoriapp.Itens;

public class ExemploItemFeedback {
    private String mTituloFeedback;
    private String mDescricaoFeedback;
    private String mDataFeedback;

    public ExemploItemFeedback(String tituloFeedback, String descricaoFeedback, String dataFeedback){
        mTituloFeedback = tituloFeedback;
        mDescricaoFeedback = descricaoFeedback;
        mDataFeedback = dataFeedback;
    }

    public String getTituloFeedback(){
        return mTituloFeedback;
    }

    public String getDescricaoFeedback(){
        return mDescricaoFeedback;
    }

    public String getDataFeedback(){
        return mDataFeedback;
    }


}
