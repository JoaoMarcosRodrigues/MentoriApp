package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.R;

public class DetalheTarefaMentorActivity extends AppCompatActivity {

    Tarefa tarefa;
    private Toolbar toolbar;
    private TextView txtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_tarefa_mentor);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtDescricao = findViewById(R.id.txtDescrição);

        Bundle bundle = getIntent().getExtras();
        tarefa = bundle.getParcelable("tarefa");

        String titulo = tarefa.getTitulo();
        getSupportActionBar().setTitle(titulo);
        String descricao = tarefa.getDescricao();

        txtDescricao.setText(descricao);
    }
}