package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemMeuMentorado;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploMeuMentoradoAdapter extends RecyclerView.Adapter<ExemploMeuMentoradoAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemMeuMentorado> mExemploListaMeuMentorado;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImgMeuMentorado;
        public TextView mNomeMentorado;
        public TextView mAreaAtuacaoMentorado;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgMeuMentorado = itemView.findViewById(R.id.img_meu_mentorado);
            mNomeMentorado = itemView.findViewById(R.id.txt_nome_mentorado);
            mAreaAtuacaoMentorado = itemView.findViewById(R.id.txt_area_atuacao_mentorado);
        }
    }

    public ExemploMeuMentoradoAdapter(ArrayList<ExemploItemMeuMentorado> exemploListaMeuMentorado){
        mExemploListaMeuMentorado = exemploListaMeuMentorado;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_meus_mentorados,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemMeuMentorado itemMeuMentoradoCorrente = mExemploListaMeuMentorado.get(position);

        holder.mImgMeuMentorado.setImageResource(itemMeuMentoradoCorrente.getImagemMeuMentorado());
        holder.mNomeMentorado.setText(itemMeuMentoradoCorrente.getNomeMentorado());
        holder.mAreaAtuacaoMentorado.setText(itemMeuMentoradoCorrente.getAreaAtuacaoMentorado());
    }

    @Override
    public int getItemCount() {
        return mExemploListaMeuMentorado.size();
    }


}
