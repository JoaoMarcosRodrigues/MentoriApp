package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.ExemploReuniaoAdapter;
import com.example.mentoriapp.Adapters.ExemploTarefaAdapter;
import com.example.mentoriapp.Itens.ExemploItemReuniao;
import com.example.mentoriapp.Itens.ExemploItemTarefa;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ListaTarefasActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        ArrayList<ExemploItemTarefa> exemploListaTarefa = new ArrayList<>();
        exemploListaTarefa.add(new ExemploItemTarefa(R.drawable.ic_tarefa,"Tarefa 1","Descrição",true));
        exemploListaTarefa.add(new ExemploItemTarefa(R.drawable.ic_tarefa,"Tarefa 2","Descrição",true));
        exemploListaTarefa.add(new ExemploItemTarefa(R.drawable.ic_tarefa,"Tarefa 3","Descrição",true));

        mRecyclerView = findViewById(R.id.recycler_tarefas);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExemploTarefaAdapter(exemploListaTarefa);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}