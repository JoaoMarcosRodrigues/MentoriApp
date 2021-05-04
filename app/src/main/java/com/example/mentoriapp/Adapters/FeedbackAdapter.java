package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FeedbackAdapter extends FirestoreRecyclerAdapter<Feedback, FeedbackAdapter.FeedbackHolder> {
    public FeedbackAdapter(@NonNull FirestoreRecyclerOptions<Feedback> options) {
        super(options);
    }

    @NonNull
    @Override
    public FeedbackAdapter.FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_feedback,
                parent,false);
        return new FeedbackAdapter.FeedbackHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedbackHolder holder, int position, @NonNull Feedback model) {
        holder.txtTitulo.setText(model.getTitulo());
        holder.txtDescricao.setText(model.getDescricao());
        holder.txtData.setText(model.getData());
    }

    class FeedbackHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtDescricao;
        TextView txtData;

        public FeedbackHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txt_titulo_feedback);
            txtDescricao = itemView.findViewById(R.id.txt_descricao_feedback);
            txtData = itemView.findViewById(R.id.txt_data_feedback);
        }
    }
}
