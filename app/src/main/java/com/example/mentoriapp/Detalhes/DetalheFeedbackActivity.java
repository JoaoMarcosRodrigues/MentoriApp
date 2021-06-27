package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.R;

public class DetalheFeedbackActivity extends AppCompatActivity {

    Feedback feedback;
    private Toolbar toolbar;
    private TextView txtDescricao,txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_feedback);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDescricao = findViewById(R.id.txtDescrição);
        txtData = findViewById(R.id.txtData);

        Bundle bundle = getIntent().getExtras();
        feedback = bundle.getParcelable("feedback");

        String titulo = feedback.getTitulo();
        getSupportActionBar().setTitle(titulo);
        String descricao = feedback.getDescricao();
        String data = feedback.getData();

        txtData.setText(data);
        txtDescricao.setText(descricao);

        //Toast.makeText(this,titulo+"-"+descricao+"-"+data,Toast.LENGTH_SHORT).show();
    }
}