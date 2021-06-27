package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class MentoradoPerfilVisitaActivity extends AppCompatActivity {

    Mentorado mentorado;
    private Toolbar toolbar;
    private ImageView imgPhotoPerfil;
    private TextView txtNome,txtEmail,txtAreaAtuacao;
    private TextInputEditText editTelefone;
    private Button btnVerRelatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorado_perfil_visita);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtNome = findViewById(R.id.txt_nome_perfil_mentorado);
        txtEmail = findViewById(R.id.txt_email_perfil_mentorado);
        txtAreaAtuacao = findViewById(R.id.txt_area_atuacao_perfil_mentorado);
        imgPhotoPerfil = findViewById(R.id.img_photo_perfil);
        editTelefone = findViewById(R.id.edit_telefone_perfil_mentorado);
        btnVerRelatos = findViewById(R.id.btnVerRelatos);

        Bundle bundle = getIntent().getExtras();
        mentorado = bundle.getParcelable("mentorado");

        String nome = mentorado.getNome();
        getSupportActionBar().setTitle(nome);
        String email = mentorado.getAreaAtuacao();
        String area = mentorado.getEmail();
        String telefone = mentorado.getEmailMentor();
        String profileUrl = mentorado.getSenha();

        txtNome.setText(nome);
        txtEmail.setText(email);
        txtAreaAtuacao.setText(area);
        editTelefone.setText(telefone);

        Picasso.get()
                .load(profileUrl)
                .into(imgPhotoPerfil);


        btnVerRelatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}