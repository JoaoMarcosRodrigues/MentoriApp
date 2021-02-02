package com.example.mentoriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout layout_email,layout_senha;
    TextInputEditText edit_email,edit_senha;
    Button botao_entrar;
    TextView link_esquecer_senha,link_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        layout_email = findViewById(R.id.input_email);
        layout_senha = findViewById(R.id.input_senha);

        link_esquecer_senha = findViewById(R.id.txt_esqueceu_senha);
        link_cadastro = findViewById(R.id.txt_link_cadastro);

        botao_entrar = findViewById(R.id.btn_entrar);
    }
}