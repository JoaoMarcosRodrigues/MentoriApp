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
import android.widget.TextView;

public class RecuperarSenhaActivity extends AppCompatActivity {

    TextView link_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);


        String text = "Ainda não possui cadastro? Clique aqui";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(RecuperarSenhaActivity.this,OpcaoCadastroActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        link_cadastro = findViewById(R.id.txt_ainda_nao_possui_cadastro);
        ss.setSpan(clickableSpan1,27,38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        link_cadastro.setText(ss);
        link_cadastro.setMovementMethod(LinkMovementMethod.getInstance());
    }

}