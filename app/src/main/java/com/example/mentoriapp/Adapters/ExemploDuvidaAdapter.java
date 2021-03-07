package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemDuvida;
import com.example.mentoriapp.Itens.ExemploItemTarefa;
import com.example.mentoriapp.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class ExemploDuvidaAdapter extends RecyclerView.Adapter<ExemploDuvidaAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemDuvida> mExemploListaDuvida;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public TextView mTituloDuvida;
        public CheckBox mFavoritoDuvida;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloDuvida = itemView.findViewById(R.id.txt_titulo_duvida);
            mFavoritoDuvida = itemView.findViewById(R.id.check_favorito_duvida);
        }
    }

    public ExemploDuvidaAdapter(ArrayList<ExemploItemDuvida> exemploListaDuvida){
        mExemploListaDuvida = exemploListaDuvida;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_duvida,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemDuvida itemDuvidaCorrente = mExemploListaDuvida.get(position);

        holder.mTituloDuvida.setText(itemDuvidaCorrente.getTituloDuvida());
        holder.mFavoritoDuvida.setChecked(itemDuvidaCorrente.getFavoritoDuvida());
    }

    @Override
    public int getItemCount() {
        return mExemploListaDuvida.size();
    }


}
