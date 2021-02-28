package com.example.mentoriapp.Mentor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentoriapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CadastroMentorActivity extends AppCompatActivity {

    TextInputLayout layout_email,layout_nome,layout_telefone,layout_senha,layout_resenha;
    TextInputEditText edit_email,edit_nome,edit_telefone,edit_senha,edit_resenha;
    Button botao_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mentor);

        botao_next = findViewById(R.id.btn_next);

        botao_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroMentorActivity.this,CadastroMentor2Activity.class));
            }
        });
    }
}