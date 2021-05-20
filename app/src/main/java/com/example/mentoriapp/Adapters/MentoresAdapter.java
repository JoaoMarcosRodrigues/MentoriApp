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
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class MentoresAdapter extends FirestoreRecyclerAdapter<Mentor, MentoresAdapter.MentoresHolder> {
    public MentoresAdapter(@NonNull FirestoreRecyclerOptions<Mentor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MentoresAdapter.MentoresHolder holder, int position, @NonNull Mentor model) {
        holder.txtNome.setText(model.getNome());
        holder.txtFormacao.setText(model.getFormacao());
        holder.txtCurriculo.setText(model.getCurriculo());
        Picasso.get()
                .load(model.getProfileUrl())
                .into(holder.imgFotoPerfil);
    }

    @NonNull
    @Override
    public MentoresAdapter.MentoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_mentores,
                parent,false);
        return new MentoresAdapter.MentoresHolder(v);
    }

    class MentoresHolder extends RecyclerView.ViewHolder{
        ImageView imgFotoPerfil;
        TextView txtNome;
        TextView txtFormacao;
        TextView txtCurriculo;
        Button btnSolicitarMentoria;

        public MentoresHolder(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txt_nome);
            txtFormacao = itemView.findViewById(R.id.txt_formacao);
            txtCurriculo = itemView.findViewById(R.id.txt_curriculo);
            imgFotoPerfil = itemView.findViewById(R.id.img_foto_perfil);
            btnSolicitarMentoria = itemView.findViewById(R.id.btnSolicitarMentoria);
        }
    }
}
