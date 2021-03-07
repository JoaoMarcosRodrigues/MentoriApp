package com.example.mentoriapp.Itens;

public class ItemMentorado {
    private String mNome;
    private String mAreaInteresse;
    private Boolean mCheckBox;

    public ItemMentorado(Boolean checkBox, String nome, String areaInteresse) {
        mNome = nome;
        mAreaInteresse = areaInteresse;
        mCheckBox = checkBox;
    }

    public String getmNome() {
        return mNome;
    }

    public String getmAreaInteresse() {
        return mAreaInteresse;
    }

    public Boolean getmCheckBox() {
        return mCheckBox;
    }

}
