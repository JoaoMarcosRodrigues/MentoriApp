package com.example.mentoriapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class OpcaoCadastroActivity extends AppCompatActivity {

    RelativeLayout mentor,mentorado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mentor = findViewById(R.id.layout_mentor);
        mentorado = findViewById(R.id.layout_mentorado);

        mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this,CadastroMentor.class);
                //startActivity(intent);
            }
        });

        mentorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this,CadastroMentorado.class);
                //startActivity(intent);
            }
        });
    }
}