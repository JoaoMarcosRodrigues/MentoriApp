package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.R;

public class DetalheDificuldadeActivity extends AppCompatActivity {

    Dificuldade dificuldade;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_dificuldade);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        dificuldade = bundle.getParcelable("dificuldade");

        String tag = dificuldade.getTagDificuldade();
        getSupportActionBar().setTitle(tag);
        String descricao = dificuldade.getDescricaoDificuldade();
        boolean check = dificuldade.isFavorito();

        Toast.makeText(this,tag+"-"+descricao+"-"+check,Toast.LENGTH_SHORT).show();
    }
}