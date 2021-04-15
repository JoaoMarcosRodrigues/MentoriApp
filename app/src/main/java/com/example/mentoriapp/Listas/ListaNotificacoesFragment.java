package com.example.mentoriapp.Listas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.ExemploAprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploNotificacaoAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Itens.ExemploItemAprendizado;
import com.example.mentoriapp.Itens.ExemploItemNotificacao;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaNotificacoesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_notificacoes, container, false);

        ArrayList<ExemploItemNotificacao> exemploListaNotificacao = new ArrayList<>();
        exemploListaNotificacao.add(new ExemploItemNotificacao("Notificação 1","Descrição"));
        exemploListaNotificacao.add(new ExemploItemNotificacao("Notificação 2","Descrição"));
        exemploListaNotificacao.add(new ExemploItemNotificacao("Notificação 3","Descrição"));

        mRecyclerView = view.findViewById(R.id.listaNotificacoes);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploNotificacaoAdapter(exemploListaNotificacao);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}