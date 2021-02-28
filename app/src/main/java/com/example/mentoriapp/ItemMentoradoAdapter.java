package com.example.mentoriapp;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemMentoradoAdapter extends RecyclerView.Adapter<ItemMentoradoAdapter.ItemMentoradoViewHolder> implements Filterable{
    private List<ItemMentorado> mItemMentorados;
    private List<ItemMentorado> mItemMentoradosFull;

    public static class ItemMentoradoViewHolder extends RecyclerView.ViewHolder{
        public TextView mAreaInteresse, mNome;
        public CheckBox mCheckBox;

        public ItemMentoradoViewHolder(@NonNull View itemView) {
            super(itemView);
            mNome = itemView.findViewById(R.id.txt_nome);
            mAreaInteresse = itemView.findViewById(R.id.txt_area_interesse);
            mCheckBox = itemView.findViewById(R.id.checkbox_mentorado);
        }
    }

    public ItemMentoradoAdapter(ArrayList<ItemMentorado> itemMentorados){
        mItemMentorados = itemMentorados;
        mItemMentoradosFull = new ArrayList<>(itemMentorados);
    }

    @NonNull
    @Override
    public ItemMentoradoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exemplo_item_mentorado,parent,false);
        ItemMentoradoViewHolder ivh = new ItemMentoradoViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMentoradoViewHolder holder, int position) {
        ItemMentorado currentItem = mItemMentorados.get(position);

        holder.mNome.setText(currentItem.getmNome());
        holder.mAreaInteresse.setText(currentItem.getmAreaInteresse());
        if(holder.mCheckBox.isChecked()){
            holder.mCheckBox.setChecked(true);
        }else{
            holder.mCheckBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mItemMentorados.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemMentorado> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mItemMentoradosFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ItemMentorado item : mItemMentoradosFull) {
                    if (item.getmNome().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItemMentorados.clear();
            mItemMentorados.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
