package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Itens.ExemploItemReuniao;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ExemploReuniaoAdapter extends RecyclerView.Adapter<ExemploReuniaoAdapter.ExemploViewHolder> {
    private ArrayList<ExemploItemReuniao> mExemploListaReuniao;

    public static class ExemploViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageReuniao;
        public TextView mTituloReuniao;
        public TextView mDescricaoReuniao;

        public ExemploViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageReuniao = itemView.findViewById(R.id.img_reuniao);
            mTituloReuniao = itemView.findViewById(R.id.txt_titulo_reuniao);
            mDescricaoReuniao = itemView.findViewById(R.id.txt_descricao_reuniao);
        }
    }

    public ExemploReuniaoAdapter(ArrayList<ExemploItemReuniao> exemploListaReuniao){
        mExemploListaReuniao = exemploListaReuniao;
    }

    @NonNull
    @Override
    public ExemploViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_reuniao,parent,false);
        ExemploViewHolder evh = new ExemploViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExemploViewHolder holder, int position) {
        ExemploItemReuniao itemReuniaoCorrente = mExemploListaReuniao.get(position);

        holder.mImageReuniao.setImageResource(itemReuniaoCorrente.getImageResourceReuniao());
        holder.mTituloReuniao.setText(itemReuniaoCorrente.getTituloReuniao());
        holder.mDescricaoReuniao.setText(itemReuniaoCorrente.getDescricaoReuniao());
    }

    @Override
    public int getItemCount() {
        return mExemploListaReuniao.size();
    }


}
