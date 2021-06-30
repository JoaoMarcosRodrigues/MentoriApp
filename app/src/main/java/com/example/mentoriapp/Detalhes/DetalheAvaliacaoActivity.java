package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Avaliacao;
import com.example.mentoriapp.R;

public class DetalheAvaliacaoActivity extends AppCompatActivity {

    Avaliacao avaliacao;
    private Toolbar toolbar;
    private TextView txtTitulo,txtDescricao,txtRelato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_avaliacao);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescrição);
        txtRelato = findViewById(R.id.txtRelato);

        Bundle bundle = getIntent().getExtras();
        avaliacao = bundle.getParcelable("avaliacao");

        String titulo = avaliacao.getTituloAvaliacao();
        getSupportActionBar().setTitle(titulo);
        String descricao = avaliacao.getDescricaoAvaliacao();
        String relato = avaliacao.getTituloRelato();

        txtTitulo.setText(titulo);
        txtDescricao.setText(descricao);
        txtRelato.setText(relato);
    }
}