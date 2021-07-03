package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.R;

public class DetalhesRelatoMentoradoActivity extends AppCompatActivity {

    Relato relato;
    private Toolbar toolbar;
    private TextView txtTema,txtDescricao,txtPresencial,txtTarefa,txtData;
    private Button btnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_relato_mentorado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTema = findViewById(R.id.txtTema);
        txtDescricao = findViewById(R.id.txtDescrição);
        txtPresencial = findViewById(R.id.txtPresencial);
        txtTarefa = findViewById(R.id.txtTarefa);
        txtData = findViewById(R.id.txtData);
        btnFeedback = findViewById(R.id.btnFeedback);

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

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicou!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}