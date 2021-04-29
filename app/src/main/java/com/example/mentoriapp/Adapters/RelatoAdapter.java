package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RelatoAdapter extends FirestoreRecyclerAdapter<Relato, RelatoAdapter.RelatoHolder> {
    public RelatoAdapter(@NonNull FirestoreRecyclerOptions<Relato> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RelatoHolder holder, int position, @NonNull Relato model) {
        holder.txtTitulo.setText(model.getTitulo());
        holder.txtTema.setText(model.getTema());
        holder.txtData.setText(model.getData());
    }

    @NonNull
    @Override
    public RelatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_relato,
                parent,false);
        return new RelatoHolder(v);
    }

    class RelatoHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtTema;
        TextView txtData;

        public RelatoHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_relato);
            txtTema = itemView.findViewById(R.id.txt_tema_relato);
            txtData = itemView.findViewById(R.id.txt_data_relato);
        }
    }
}
