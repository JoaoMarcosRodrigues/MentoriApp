package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemMeuMentorado;
import com.example.mentoriapp.Itens.ExemploItemTarefa;
import com.example.mentoriapp.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class ExemploTarefaAdapter extends RecyclerView.Adapter<ExemploTarefaAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemTarefa> mExemploListaTarefa;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImgTarefa;
        public TextView mTituloTarefa;
        public TextView mDescricaoTarefa;
        public Chip mStatusTarefa;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgTarefa = itemView.findViewById(R.id.img_tarefa);
            mTituloTarefa = itemView.findViewById(R.id.txt_titulo_tarefa);
            mDescricaoTarefa = itemView.findViewById(R.id.txt_descricao_tarefa);
            mStatusTarefa = itemView.findViewById(R.id.status_tarefa);
        }
    }

    public ExemploTarefaAdapter(ArrayList<ExemploItemTarefa> exemploListaTarefa){
        mExemploListaTarefa = exemploListaTarefa;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_tarefa,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemTarefa itemTarefaCorrente = mExemploListaTarefa.get(position);

        holder.mImgTarefa.setImageResource(itemTarefaCorrente.getImgTarefa());
        holder.mTituloTarefa.setText(itemTarefaCorrente.getTituloTarefa());
        holder.mDescricaoTarefa.setText(itemTarefaCorrente.getDescricaoTarefa());
        holder.mStatusTarefa.setCheckable(itemTarefaCorrente.getStatusTarefa());
    }

    @Override
    public int getItemCount() {
        return mExemploListaTarefa.size();
    }


}
