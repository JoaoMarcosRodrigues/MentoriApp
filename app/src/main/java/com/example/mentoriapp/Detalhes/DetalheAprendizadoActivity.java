package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.R;

public class DetalheAprendizadoActivity extends AppCompatActivity {

    Aprendizado aprendizado;
    private Toolbar toolbar;
    private TextView txtDescricao,txtRelato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_aprendizado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtDescricao = findViewById(R.id.txtDescrição);
        txtRelato = findViewById(R.id.txtRelato);

        Bundle bundle = getIntent().getExtras();
        aprendizado = bundle.getParcelable("aprendizado");

        String titulo = aprendizado.getTituloAprendizado();
        getSupportActionBar().setTitle(titulo);
        String descricao = aprendizado.getDescricaoAprendizado();
        String relato = aprendizado.getTituloRelato();

        txtDescricao.setText(descricao);
        txtRelato.setText(relato);

        //Toast.makeText(this,titulo+"-"+descricao,Toast.LENGTH_SHORT).show();
    }
}