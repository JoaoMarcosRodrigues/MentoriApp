package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.R;

public class DetalheRelatoActivity extends AppCompatActivity {

    Relato relato;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_relato);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        relato = bundle.getParcelable("relato");

        String titulo = relato.getTitulo();
        getSupportActionBar().setTitle(titulo);
        String tema = relato.getTema();
        String data = relato.getData();

        Toast.makeText(this,titulo+"-"+tema+"-"+data,Toast.LENGTH_SHORT).show();
    }
}