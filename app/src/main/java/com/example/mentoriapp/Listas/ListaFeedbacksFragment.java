package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Adapters.ExemploFeedbackAdapter;
import com.example.mentoriapp.Cadastro.CadastroFeedbackFragment;
import com.example.mentoriapp.Itens.ExemploItemFeedback;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaFeedbacksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mBtnAdicionarFeedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_feedbacks, container, false);

        ArrayList<ExemploItemFeedback> exemploListaFeedback = new ArrayList<>();
        exemploListaFeedback.add(new ExemploItemFeedback("Feedback 1","Descrição","07/03/2021"));
        exemploListaFeedback.add(new ExemploItemFeedback("Feedback 1","Descrição","07/03/2021"));
        exemploListaFeedback.add(new ExemploItemFeedback("Feedback 1","Descrição","07/03/2021"));

        mRecyclerView = view.findViewById(R.id.recycler_feedbacks);
        mBtnAdicionarFeedback = view.findViewById(R.id.btnAdicionarFeedback);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExemploFeedbackAdapter(exemploListaFeedback);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mBtnAdicionarFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentor, new CadastroFeedbackFragment())
                        .commit();
            }
        });

        return view;
    }
}