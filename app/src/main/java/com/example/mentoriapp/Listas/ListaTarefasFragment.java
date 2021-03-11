package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Adapters.ExemploTarefaAdapter;
import com.example.mentoriapp.Itens.ExemploItemTarefa;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ListaTarefasFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_tarefas, container, false);

        ArrayList<ExemploItemTarefa> exemploListaTarefa = new ArrayList<>();
        exemploListaTarefa.add(new ExemploItemTarefa(R.drawable.ic_tarefa,"Tarefa 1","Descrição",true));
        exemploListaTarefa.add(new ExemploItemTarefa(R.drawable.ic_tarefa,"Tarefa 2","Descrição",true));
        exemploListaTarefa.add(new ExemploItemTarefa(R.drawable.ic_tarefa,"Tarefa 3","Descrição",true));

        mRecyclerView = view.findViewById(R.id.listaTarefas);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ExemploTarefaAdapter(exemploListaTarefa);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}