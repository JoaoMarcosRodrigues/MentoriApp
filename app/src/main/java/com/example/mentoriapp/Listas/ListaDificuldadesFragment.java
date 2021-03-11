package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.ExemploDificuldadeAdapter;
import com.example.mentoriapp.Cadastro.CadastroDificuldadeFragment;
import com.example.mentoriapp.Itens.ExemploItemDificuldade;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaDificuldadesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_dificuldades, container, false);

        FloatingActionButton addDificuldade = view.findViewById(R.id.btnAdicionarDificuldade);

        addDificuldade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CadastroDificuldadeFragment.class));
            }
        });

        ArrayList<ExemploItemDificuldade> exemploListaDificuldade = new ArrayList<>();
        exemploListaDificuldade.add(new ExemploItemDificuldade("Dificuldade 1",true));
        exemploListaDificuldade.add(new ExemploItemDificuldade("Dificuldade 2",true));
        exemploListaDificuldade.add(new ExemploItemDificuldade("Dificuldade 3",false));

        mRecyclerView = view.findViewById(R.id.listaDificuldades);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploDificuldadeAdapter(exemploListaDificuldade);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}