package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Cadastro.CadastroReuniaoActivity;
import com.example.mentoriapp.Itens.ExemploItemReuniao;
import com.example.mentoriapp.Adapters.ExemploReuniaoAdapter;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaReunioesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_reunioes, container, false);

        FloatingActionButton addReuniao = view.findViewById(R.id.btnAdicionarReuniao);

        addReuniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CadastroReuniaoActivity.class));
            }
        });

        ArrayList<ExemploItemReuniao> exemploListaReuniao = new ArrayList<>();
        exemploListaReuniao.add(new ExemploItemReuniao(R.drawable.ic_reuniao,"Reunião 1","Descrição"));
        exemploListaReuniao.add(new ExemploItemReuniao(R.drawable.ic_reuniao,"Reunião 2","Descrição"));
        exemploListaReuniao.add(new ExemploItemReuniao(R.drawable.ic_reuniao,"Reunião 3","Descrição"));

        mRecyclerView = view.findViewById(R.id.listaReunioes);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploReuniaoAdapter(exemploListaReuniao);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}