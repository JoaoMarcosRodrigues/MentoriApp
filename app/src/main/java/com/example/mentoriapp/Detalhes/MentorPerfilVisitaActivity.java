package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class MentorPerfilVisitaActivity extends AppCompatActivity {

    Mentor mentor;
    private Toolbar toolbar;
    private ImageView imgPhotoPerfil;
    private TextView txtNome,txtEmail,txtAreaAtuacao,txtTempoExperiencia;
    private TextInputEditText editTelefone,editFormacao,editCurriculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_perfil_visita);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtNome = findViewById(R.id.txt_nome_perfil_mentor);
        txtEmail = findViewById(R.id.txt_email_perfil_mentor);
        txtAreaAtuacao = findViewById(R.id.txt_area_atuacao_perfil_mentor);
        txtTempoExperiencia = findViewById(R.id.txt_tempo_experiencia);
        imgPhotoPerfil = findViewById(R.id.img_photo_perfil);
        editTelefone = findViewById(R.id.edit_telefone_perfil_mentor);
        editFormacao = findViewById(R.id.edit_formacao_perfil_mentor);
        editCurriculo = findViewById(R.id.edit_curriculo_perfil_mentor);

        Bundle bundle = getIntent().getExtras();
        mentor = bundle.getParcelable("mentor");

        String nome = mentor.getSenha(); // OK!
        getSupportActionBar().setTitle(nome);
        String email = mentor.getEmail(); // OK!
        String area = mentor.getProfileUrl(); // OK!
        String telefone = mentor.getNome(); // OK!
        String profileUrl = mentor.getTelefone(); // OK!
        String curriulo = mentor.getFormacao(); // OK!
        String formacao = mentor.getCurriculo();
        String tempoExperiencia = mentor.getTempoAtuacao();

        Toast.makeText(this,"Formação: "+formacao+"\nTempo: "+tempoExperiencia,Toast.LENGTH_SHORT).show();

        txtNome.setText(nome); // OK!
        txtEmail.setText(email); // OK!
        txtAreaAtuacao.setText(area); // OK!
        editTelefone.setText(telefone); // OK!
        editCurriculo.setText(curriulo); // Ok!
        editFormacao.setText(formacao);
        txtTempoExperiencia.setText(tempoExperiencia);

        Picasso.get()
                .load(profileUrl)
                .into(imgPhotoPerfil);
    }
}