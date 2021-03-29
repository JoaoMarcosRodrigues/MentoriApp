package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecuperarSenhaActivity extends AppCompatActivity {

    TextView link_cadastro;
    Button btnRecuperar;
    TextInputEditText editEmail;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        progressDialog = new ProgressDialog(this);
        editEmail = findViewById(R.id.edit_email);
        firebaseAuth = FirebaseAuth.getInstance();

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
        btnRecuperar = findViewById(R.id.btn_recuperar);

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RecuperarSenhaActivity.this,"Campo email e senha obrigatórios!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RecuperarSenhaActivity.this,"Por favor, insira um email válido!",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Verificando os dados...");
                progressDialog.show();

                atualizarSenha(email);
            }
        });

        ss.setSpan(clickableSpan1,27,38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        link_cadastro.setText(ss);
        link_cadastro.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void atualizarSenha(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(RecuperarSenhaActivity.this,"Verifique seu email para alterar a senha!",Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(RecuperarSenhaActivity.this,"Tente novamente! Algo de errado aconteceu!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}