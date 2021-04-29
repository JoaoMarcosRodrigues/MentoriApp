package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DificuldadeAdapter extends FirestoreRecyclerAdapter<Dificuldade, DificuldadeAdapter.DificuldadeHolder> {
    public DificuldadeAdapter(@NonNull FirestoreRecyclerOptions<Dificuldade> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DificuldadeHolder holder, int position, @NonNull Dificuldade model) {
        holder.txtTag.setText(model.getTagDificuldade());
        holder.txtDescricao.setText(model.getDescricaoDificuldade());
        holder.checkBox.setChecked(model.isFavorito());
    }

    @NonNull
    @Override
    public DificuldadeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_dificuldade,
                parent,false);
        return new DificuldadeHolder(v);
    }

    class DificuldadeHolder extends RecyclerView.ViewHolder{
        TextView txtTag;
        TextView txtDescricao;
        CheckBox checkBox;

        public DificuldadeHolder(@NonNull View itemView) {
            super(itemView);

            txtTag = itemView.findViewById(R.id.txt_titulo_dificuldade);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_dificuldade);
            checkBox = itemView.findViewById(R.id.check_favorito_dificuldade);
        }
    }
}
