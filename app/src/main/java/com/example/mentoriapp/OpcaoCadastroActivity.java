package com.example.mentoriapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mentoriapp.Mentor.CadastroMentorActivity;
import com.example.mentoriapp.Mentorado.CadastroMentoradoActivity;

public class OpcaoCadastroActivity extends AppCompatActivity {

    GridLayout main_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao_cadastro);

        main_grid = findViewById(R.id.grid_layout);
        setSingleAction(main_grid);

    }

    private void setSingleAction(GridLayout main_grid){
        for(int i=0; i<main_grid.getChildCount(); i++){
            CardView cardView = (CardView) main_grid.getChildAt(i);
            int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (finalI){
                        case 0:
                            startActivity(new Intent(OpcaoCadastroActivity.this,CadastroMentorActivity.class));
                            break;

                        case 1:
                            startActivity(new Intent(OpcaoCadastroActivity.this, CadastroMentoradoActivity.class));
                            break;
                    }
                }
            });
        }
    }

}