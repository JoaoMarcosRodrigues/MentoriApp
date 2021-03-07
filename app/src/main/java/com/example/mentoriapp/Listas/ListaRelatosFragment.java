package com.example.mentoriapp.Listas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.ExemploRelatoAdapter;
import com.example.mentoriapp.Adapters.ExemploReuniaoAdapter;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.Itens.ExemploItemReuniao;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaRelatosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_relatos, container, false);

        FloatingActionButton addRelato = view.findViewById(R.id.btnAdicionarRelato);

        addRelato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), CadastroReuniaoActivity.class));
            }
        });

        ArrayList<ExemploItemRelato> exemploListaRelato = new ArrayList<>();
        exemploListaRelato.add(new ExemploItemRelato("Relato 1","Tema 1","07/03/2021"));
        exemploListaRelato.add(new ExemploItemRelato("Relato 1","Tema 1","07/03/2021"));
        exemploListaRelato.add(new ExemploItemRelato("Relato 1","Tema 1","07/03/2021"));

        mRecyclerView = view.findViewById(R.id.listaRelatos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploRelatoAdapter(exemploListaRelato);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}