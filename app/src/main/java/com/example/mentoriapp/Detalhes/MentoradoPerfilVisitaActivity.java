package com.example.mentoriapp.Detalhes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.Classes.Mentoria;
import com.example.mentoriapp.Listas.ListaRelatosMentoradoActivity;
import com.example.mentoriapp.Listas.MeusMentoradosFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.List;

public class MentoradoPerfilVisitaActivity extends AppCompatActivity {

    Mentorado mentorado;
    private Toolbar toolbar;
    private ImageView imgPhotoPerfil;
    private TextView txtNome,txtEmail,txtAreaAtuacao;
    private TextInputEditText editTelefone;
    private Button btnVerRelatos;
    private String email,nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorado_perfil_visita);

        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        txtNome = findViewById(R.id.txt_nome_perfil_mentorado);
        txtEmail = findViewById(R.id.txt_email_perfil_mentorado);
        txtAreaAtuacao = findViewById(R.id.txt_area_atuacao_perfil_mentorado);
        imgPhotoPerfil = findViewById(R.id.img_photo_perfil);
        editTelefone = findViewById(R.id.edit_telefone_perfil_mentorado);
        btnVerRelatos = findViewById(R.id.btnVerRelatos);

        Bundle bundle = getIntent().getExtras();
        mentorado = bundle.getParcelable("mentorado");

        nome = mentorado.getNome();
        getSupportActionBar().setTitle(nome);
        email = mentorado.getAreaAtuacao();
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
                Intent intent = new Intent(getApplicationContext(), ListaRelatosMentoradoActivity.class);
                intent.putExtra("emailMentorado", email);
                intent.putExtra("nomeMentorado",nome);

                startActivity(intent);
            }
        });
    }
}