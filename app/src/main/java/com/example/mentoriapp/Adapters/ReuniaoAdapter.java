package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Reuniao;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class ReuniaoAdapter extends FirestoreRecyclerAdapter<Reuniao, ReuniaoAdapter.ReuniaoHolder> {

    public ReuniaoAdapter(@NonNull FirestoreRecyclerOptions<Reuniao> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReuniaoHolder holder, int position, @NonNull Reuniao model) {
        holder.txtTitulo.setText(model.getTitulo());
        holder.txtDescricao.setText(model.getDescricao());
        holder.txtHorario.setText(model.getHorario());
        holder.txtData.setText(model.getData());
    }

    @NonNull
    @Override
    public ReuniaoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_reuniao,
                parent,false);
        return new ReuniaoAdapter.ReuniaoHolder(v);
    }


    class ReuniaoHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtDescricao;
        TextView txtHorario;
        TextView txtData;

        public ReuniaoHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_reuniao);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_reuniao);
            txtHorario = itemView.findViewById(R.id.txt_horario_reuniao);
            txtData = itemView.findViewById(R.id.txt_data_reuniao);
        }
    }
}
