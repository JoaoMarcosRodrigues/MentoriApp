package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Mentor.CadastroMentorActivity;
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

        String text = "Ainda não possui cadastro? Clique aqui";
        String text2 = "Esqueceu a senha?";

        SpannableString ss = new SpannableString(text);
        SpannableString ss2 = new SpannableString(text2);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this,OpcaoCadastroActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this,RecuperarSenhaActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        layout_email = findViewById(R.id.input_email);
        layout_senha = findViewById(R.id.input_senha);

        link_esquecer_senha = findViewById(R.id.txt_esqueceu_senha);
        link_cadastro = findViewById(R.id.txt_ainda_nao_possui_cadastro);

        botao_entrar = findViewById(R.id.btn_entrar);

        ss.setSpan(clickableSpan1,27,38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(clickableSpan2,0,17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        link_cadastro.setText(ss);
        link_cadastro.setMovementMethod(LinkMovementMethod.getInstance());

        link_esquecer_senha.setText(ss2);
        link_esquecer_senha.setMovementMethod(LinkMovementMethod.getInstance());

        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Entrou!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}