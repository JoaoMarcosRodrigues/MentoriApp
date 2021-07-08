package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemDificuldade;
import com.example.mentoriapp.Itens.ExemploItemDuvida;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploDificuldadeAdapter extends RecyclerView.Adapter<ExemploDificuldadeAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemDificuldade> mExemploListaDificuldade;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public TextView mTituloDificuldade;
        public CheckBox mFavoritoDificuldade;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloDificuldade = itemView.findViewById(R.id.txt_tag_dificuldade);
        }
    }

    public ExemploDificuldadeAdapter(ArrayList<ExemploItemDificuldade> exemploListaDificuldade){
        mExemploListaDificuldade = exemploListaDificuldade;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_dificuldade,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemDificuldade itemDificuldadeCorrente = mExemploListaDificuldade.get(position);

        holder.mTituloDificuldade.setText(itemDificuldadeCorrente.getTituloDificuldade());
        holder.mFavoritoDificuldade.setChecked(itemDificuldadeCorrente.getFavoritoDificuldade());
    }

    @Override
    public int getItemCount() {
        return mExemploListaDificuldade.size();
    }


}
