package com.example.mentoriapp.Listas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mentoriapp.Adapters.ExemploFeedbackAdapter;
import com.example.mentoriapp.Adapters.ExemploRelatoAdapter;
import com.example.mentoriapp.Itens.ExemploItemFeedback;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;

import java.util.ArrayList;

public class ListaFeedbacksActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_feedbacks);

        ArrayList<ExemploItemFeedback> exemploListaFeedback = new ArrayList<>();
        exemploListaFeedback.add(new ExemploItemFeedback("Feedback 1","Descrição","07/03/2021"));
        exemploListaFeedback.add(new ExemploItemFeedback("Feedback 1","Descrição","07/03/2021"));
        exemploListaFeedback.add(new ExemploItemFeedback("Feedback 1","Descrição","07/03/2021"));

        mRecyclerView = findViewById(R.id.listaRelatos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExemploFeedbackAdapter(exemploListaFeedback);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}