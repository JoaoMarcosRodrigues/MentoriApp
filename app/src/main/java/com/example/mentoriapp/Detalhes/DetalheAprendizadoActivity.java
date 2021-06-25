package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.R;

public class DetalheAprendizadoActivity extends AppCompatActivity {

    Aprendizado aprendizado;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_aprendizado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        aprendizado = bundle.getParcelable("aprendizado");

        String titulo = aprendizado.getTituloAprendizado();
        getSupportActionBar().setTitle(titulo);
        String descricao = aprendizado.getDescricaoAprendizado();

        Toast.makeText(this,titulo+"-"+descricao,Toast.LENGTH_SHORT).show();
    }
}