package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.ExemploAprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploReuniaoAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoActivity;
import com.example.mentoriapp.Itens.ExemploItemAprendizado;
import com.example.mentoriapp.Itens.ExemploItemReuniao;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaAprendizadosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_aprendizados, container, false);

        FloatingActionButton addAprendizado = view.findViewById(R.id.btnAdicionarAprendizado);

        addAprendizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CadastroAprendizadoActivity.class));
            }
        });

        ArrayList<ExemploItemAprendizado> exemploListaAprendizado = new ArrayList<>();
        exemploListaAprendizado.add(new ExemploItemAprendizado("Aprendizado 1","Descrição"));
        exemploListaAprendizado.add(new ExemploItemAprendizado("Aprendizado 2","Descrição"));
        exemploListaAprendizado.add(new ExemploItemAprendizado("Aprendizado 3","Descrição"));

        mRecyclerView = view.findViewById(R.id.listaAprendizados);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploAprendizadoAdapter(exemploListaAprendizado);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}