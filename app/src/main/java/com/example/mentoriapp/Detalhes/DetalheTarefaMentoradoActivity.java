package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.R;

public class DetalheTarefaMentoradoActivity extends AppCompatActivity {

    Tarefa tarefa;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_tarefa_mentorado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        tarefa = bundle.getParcelable("tarefa");

        String titulo = tarefa.getTitulo();
        getSupportActionBar().setTitle(titulo);
        String descricao = tarefa.getDescricao();
        boolean status = tarefa.isStatus();

        Toast.makeText(this,titulo+"-"+descricao+"-"+status,Toast.LENGTH_SHORT).show();
    }
}