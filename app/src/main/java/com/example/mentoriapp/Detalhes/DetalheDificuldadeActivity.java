package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.R;

public class DetalheDificuldadeActivity extends AppCompatActivity {

    Dificuldade dificuldade;
    private Toolbar toolbar;
    private TextView txtDescricao,txtRelato;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_dificuldade);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDescricao = findViewById(R.id.txtDescrição);
        txtRelato = findViewById(R.id.txtRelato);
        checkBox = findViewById(R.id.check_favorito_dificuldade);

        Bundle bundle = getIntent().getExtras();
        dificuldade = bundle.getParcelable("dificuldade");

        String tag = dificuldade.getTagDificuldade();
        getSupportActionBar().setTitle(tag);
        String descricao = dificuldade.getDescricaoDificuldade();
        String relato = dificuldade.getTituloRelato();
        boolean check = dificuldade.isFavorito();

        txtRelato.setText(relato);
        txtDescricao.setText(descricao);
        if(check == true){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }

        //Toast.makeText(this,tag+"-"+descricao+"-"+check,Toast.LENGTH_SHORT).show();
    }
}