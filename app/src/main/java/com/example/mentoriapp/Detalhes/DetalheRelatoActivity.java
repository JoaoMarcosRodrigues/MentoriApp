package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.R;

public class DetalheRelatoActivity extends AppCompatActivity {

    Relato relato;
    private Toolbar toolbar;
    private TextView txtTema,txtDescricao,txtPresencial,txtTarefa,txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_relato);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTema = findViewById(R.id.txtTema);
        txtDescricao = findViewById(R.id.txtDescrição);
        txtPresencial = findViewById(R.id.txtPresencial);
        txtTarefa = findViewById(R.id.txtTarefa);
        txtData = findViewById(R.id.txtData);

        Bundle bundle = getIntent().getExtras();
        relato = bundle.getParcelable("relato");

        String titulo = relato.getTitulo();
        getSupportActionBar().setTitle(titulo);

        String tema = relato.getTema();
        String descricao = relato.getDescricao();
        String presencial = relato.getPresencial();
        String tarefaAssociada = relato.getTarefa_associada();
        String data = relato.getData();

        if(presencial.equals("Sim"))
            txtPresencial.setText("S");
        else
            txtPresencial.setText("N");

        txtTema.setText(tema);
        txtDescricao.setText(descricao);
        //txtPresencial.setText(presencial);
        txtTarefa.setText(tarefaAssociada);
        txtData.setText(data);

        //Toast.makeText(this,titulo+"-"+tema+"-"+data,Toast.LENGTH_SHORT).show();
    }
}