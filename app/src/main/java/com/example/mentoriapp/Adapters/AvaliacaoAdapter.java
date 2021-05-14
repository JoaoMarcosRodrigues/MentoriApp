package com.example.mentoriapp.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Avaliacao;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AvaliacaoAdapter  extends FirestoreRecyclerAdapter<Avaliacao, AvaliacaoAdapter.AvaliacaoHolder> {
    public AvaliacaoAdapter(@NonNull FirestoreRecyclerOptions<Avaliacao> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AvaliacaoAdapter.AvaliacaoHolder holder, int position, @NonNull Avaliacao model) {
        holder.txtTitulo.setText(model.getTituloAvaliacao());
        holder.txtDescricao.setText(model.getDescricaoAvaliacao());
    }

    @NonNull
    @Override
    public AvaliacaoAdapter.AvaliacaoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_avaliacao,
                parent,false);
        return new AvaliacaoAdapter.AvaliacaoHolder(v);
    }

    class AvaliacaoHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtDescricao;

        public AvaliacaoHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_avaliacao);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_avaliacao);
        }
    }

}
