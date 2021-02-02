package com.example.mentoriapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RecuperarSenhaActivity extends AppCompatActivity {

    TextInputLayout layout_email,layout_senha,layout_resenha;
    TextInputEditText edit_email,edit_senha,edit_resenha;
    Button botao_recuperar;
    TextView link_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        edit_resenha = findViewById(R.id.edit_resenha);

        layout_email = findViewById(R.id.input_email);
        layout_senha = findViewById(R.id.input_senha);
        layout_resenha = findViewById(R.id.input_resenha);

        link_cadastro = findViewById(R.id.txt_link_cadastro);

        botao_recuperar = findViewById(R.id.btn_recuperar);
    }
}