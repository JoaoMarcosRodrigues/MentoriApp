package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemAprendizado;
import com.example.mentoriapp.Itens.ExemploItemReuniao;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploAprendizadoAdapter extends RecyclerView.Adapter<ExemploAprendizadoAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemAprendizado> mExemploListaAprendizado;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public TextView mTituloAprendizado;
        public TextView mDescricaoAprendizado;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloAprendizado = itemView.findViewById(R.id.txt_titulo_aprendizado);
            mDescricaoAprendizado = itemView.findViewById(R.id.txt_descricao_aprendizado);
        }
    }

    public ExemploAprendizadoAdapter(ArrayList<ExemploItemAprendizado> exemploListaAprendizado){
        mExemploListaAprendizado = exemploListaAprendizado;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_aprendizado,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemAprendizado itemAprendizadoCorrente = mExemploListaAprendizado.get(position);

        holder.mTituloAprendizado.setText(itemAprendizadoCorrente.getTituloAprendizado());
        holder.mDescricaoAprendizado.setText(itemAprendizadoCorrente.getDescricaoAprendizado());
    }

    @Override
    public int getItemCount() {
        return mExemploListaAprendizado.size();
    }


}
