package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.R;

public class DetalheTarefaMentoradoActivity extends AppCompatActivity {

    Tarefa tarefa;
    private Toolbar toolbar;
    private TextView txtDescricao;
    private Switch switchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_tarefa_mentorado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtDescricao = findViewById(R.id.txtDescrição);
        switchStatus = findViewById(R.id.switchStatus);

        Bundle bundle = getIntent().getExtras();
        tarefa = bundle.getParcelable("tarefa");

        String titulo = tarefa.getTitulo();
        getSupportActionBar().setTitle(titulo);
        String descricao = tarefa.getDescricao();
        boolean status = tarefa.isStatus();

        txtDescricao.setText(descricao);
        if(status == true){
            switchStatus.setChecked(true);
        }else{
            switchStatus.setChecked(false);
        }

        //Toast.makeText(this,titulo+"-"+descricao+"-"+status,Toast.LENGTH_SHORT).show();
    }
}