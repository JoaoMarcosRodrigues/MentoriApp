package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemAprendizado;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploRelatoAdapter extends RecyclerView.Adapter<ExemploRelatoAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemRelato> mExemploListaRelato;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public TextView mTituloRelato;
        public TextView mTemaRelato;
        public TextView mDataRelato;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloRelato = itemView.findViewById(R.id.txt_titulo_relato);
            mTemaRelato = itemView.findViewById(R.id.txt_tema_relato);
            mDataRelato = itemView.findViewById(R.id.txt_data_relato);
        }
    }

    public ExemploRelatoAdapter(ArrayList<ExemploItemRelato> exemploListaRelato){
        mExemploListaRelato = exemploListaRelato;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_relato,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemRelato itemRelatoCorrente = mExemploListaRelato.get(position);

        holder.mTituloRelato.setText(itemRelatoCorrente.getTituloRelato());
        holder.mTemaRelato.setText(itemRelatoCorrente.getTemaRelato());
        holder.mDataRelato.setText(itemRelatoCorrente.getDataRelato());
    }

    @Override
    public int getItemCount() {
        return mExemploListaRelato.size();
    }


}
