package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AprendizadoAdapter extends FirestoreRecyclerAdapter<Aprendizado, AprendizadoAdapter.AprendizadoHolder> {
    public AprendizadoAdapter(@NonNull FirestoreRecyclerOptions<Aprendizado> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AprendizadoHolder holder, int position, @NonNull Aprendizado model) {
        holder.txtTitulo.setText(model.getTituloAprendizado());
        holder.txtDescricao.setText(model.getDescricaoAprendizado());
    }

    @NonNull
    @Override
    public AprendizadoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_aprendizado,
                parent,false);
        return new AprendizadoHolder(v);
    }

    class AprendizadoHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtDescricao;

        public AprendizadoHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_aprendizado);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_aprendizado);
        }
    }
}
