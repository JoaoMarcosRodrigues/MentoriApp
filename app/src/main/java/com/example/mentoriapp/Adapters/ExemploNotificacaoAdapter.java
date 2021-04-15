package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemAprendizado;
import com.example.mentoriapp.Itens.ExemploItemNotificacao;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploNotificacaoAdapter extends RecyclerView.Adapter<ExemploNotificacaoAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemNotificacao> mExemploListaNotificacao;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public TextView mTituloNotificacao;
        public TextView mDescricaoNotificacao;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloNotificacao = itemView.findViewById(R.id.txt_titulo_notificacao);
            mDescricaoNotificacao = itemView.findViewById(R.id.txt_descricao_notificacao);
        }
    }

    public ExemploNotificacaoAdapter(ArrayList<ExemploItemNotificacao> exemploListaNotificacao){
        mExemploListaNotificacao = exemploListaNotificacao;
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
        ExemploItemNotificacao itemAprendizadoCorrente = mExemploListaNotificacao.get(position);

        holder.mTituloNotificacao.setText(itemAprendizadoCorrente.getTituloAprendizado());
        holder.mDescricaoNotificacao.setText(itemAprendizadoCorrente.getDescricaoAprendizado());
    }

    @Override
    public int getItemCount() {
        return mExemploListaNotificacao.size();
    }


}
