package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TarefaAdapter extends FirestoreRecyclerAdapter<Tarefa, TarefaAdapter.TarefaHolder> {
    public TarefaAdapter(@NonNull FirestoreRecyclerOptions<Tarefa> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TarefaAdapter.TarefaHolder holder, int position, @NonNull Tarefa model) {
        holder.txtTitulo.setText(model.getTitulo());
        holder.txtDescricao.setText(model.getDescricao());
    }

    @NonNull
    @Override
    public TarefaAdapter.TarefaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_tarefa,
                parent,false);
        return new TarefaAdapter.TarefaHolder(v);
    }

    class TarefaHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtDescricao;

        public TarefaHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_tarefa);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_tarefa);
        }
    }
}
