package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MeusMentoradosAdapter extends FirestoreRecyclerAdapter<Mentorado, MeusMentoradosAdapter.MentoresHolder> {
    public MeusMentoradosAdapter(@NonNull FirestoreRecyclerOptions<Mentorado> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MeusMentoradosAdapter.MentoresHolder holder, int position, @NonNull Mentorado model) {
        holder.txtNome.setText(model.getNome());
        holder.txtAreaAtuacao.setText(model.getAreaAtuacao());
    }

    @NonNull
    @Override
    public MeusMentoradosAdapter.MentoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_meus_mentorados,
                parent,false);
        return new MeusMentoradosAdapter.MentoresHolder(v);
    }

    class MentoresHolder extends RecyclerView.ViewHolder{
        ImageView imgFotoPerfil;
        TextView txtNome;
        TextView txtAreaAtuacao;

        public MentoresHolder(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txt_nome_mentorado);
            txtAreaAtuacao = itemView.findViewById(R.id.txt_area_atuacao_mentorado);
            imgFotoPerfil = itemView.findViewById(R.id.img_meu_mentorado);
        }
    }
}
