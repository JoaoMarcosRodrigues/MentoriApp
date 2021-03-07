package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemFeedback;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploFeedbackAdapter extends RecyclerView.Adapter<ExemploFeedbackAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemFeedback> mExemploListaFeedback;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public TextView mTituloFeedback;
        public TextView mDescricaoFeedback;
        public TextView mDataFeedback;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloFeedback = itemView.findViewById(R.id.txt_titulo_feedback);
            mDescricaoFeedback = itemView.findViewById(R.id.txt_descricao_feedback);
            mDataFeedback = itemView.findViewById(R.id.txt_data_feedback);
        }
    }

    public ExemploFeedbackAdapter(ArrayList<ExemploItemFeedback> exemploListaFeedback){
        mExemploListaFeedback = exemploListaFeedback;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_feedback,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemFeedback itemFeedbackCorrente = mExemploListaFeedback.get(position);

        holder.mTituloFeedback.setText(itemFeedbackCorrente.getTituloFeedback());
        holder.mDescricaoFeedback.setText(itemFeedbackCorrente.getDescricaoFeedback());
        holder.mDataFeedback.setText(itemFeedbackCorrente.getDataFeedback());
    }

    @Override
    public int getItemCount() {
        return mExemploListaFeedback.size();
    }


}
