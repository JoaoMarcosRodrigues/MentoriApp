package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Adapters.ExemploMeuMentoradoAdapter;
import com.example.mentoriapp.Adapters.ExemploRelatoAdapter;
import com.example.mentoriapp.Itens.ExemploItemMeuMentorado;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class MeusMentoradosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_mentorados, container, false);

        ArrayList<ExemploItemMeuMentorado> exemploListaMeusMentorados = new ArrayList<>();
        exemploListaMeusMentorados.add(new ExemploItemMeuMentorado(R.drawable.ic_perfil,"Mentorado 1","Back-End"));
        exemploListaMeusMentorados.add(new ExemploItemMeuMentorado(R.drawable.ic_perfil,"Mentorado 2","Front-End"));
        exemploListaMeusMentorados.add(new ExemploItemMeuMentorado(R.drawable.ic_perfil,"Mentorado 3","Manutenção"));

        mRecyclerView = view.findViewById(R.id.recycler_meus_mentorados);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploMeuMentoradoAdapter(exemploListaMeusMentorados);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}