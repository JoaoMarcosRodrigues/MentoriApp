package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout layout_email, layout_senha;
    private TextInputEditText edit_email, edit_senha;
    private Button botao_entrar;
    private TextView link_esquecer_senha, link_cadastro;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private CheckBox mCheckBox;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String text = "Ainda não tem cadastro? Cadastre-se";
        String text2 = "Esqueceu a senha?";

        SpannableString ss = new SpannableString(text);
        SpannableString ss2 = new SpannableString(text2);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this, OpcaoCadastroActivity.class));
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
                startActivity(new Intent(LoginActivity.this, RecuperarSenhaActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        layout_email = findViewById(R.id.input_email);
        layout_senha = findViewById(R.id.input_senha);
        mCheckBox = findViewById(R.id.check_login);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        checkSharedPreferences();

        link_esquecer_senha = findViewById(R.id.txt_esqueceu_senha);
        link_cadastro = findViewById(R.id.txt_ainda_nao_possui_cadastro);

        botao_entrar = findViewById(R.id.btn_entrar);

        ss.setSpan(clickableSpan1, 24, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(clickableSpan2, 0, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        link_cadastro.setText(ss);
        link_cadastro.setMovementMethod(LinkMovementMethod.getInstance());

        link_esquecer_senha.setText(ss2);
        link_esquecer_senha.setMovementMethod(LinkMovementMethod.getInstance());

        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email e senha devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Verificando os dados...");
                progressDialog.show();

                if(mCheckBox.isChecked()){
                    // Salva o checkbox
                    mEditor.putString(getString(R.string.checkbox),"True");
                    mEditor.commit();

                    // Salva o email
                    mEditor.putString(getString(R.string.email),email);
                    mEditor.commit();

                    // Salva a senha
                    mEditor.putString(getString(R.string.senha),senha);
                    mEditor.commit();
                }else{
                    // Salva o checkbox
                    mEditor.putString(getString(R.string.checkbox),"False");
                    mEditor.commit();

                    // Salva o email
                    mEditor.putString(getString(R.string.email),"");
                    mEditor.commit();

                    // Salva a senha
                    mEditor.putString(getString(R.string.senha),"");
                    mEditor.commit();
                }

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    DocumentReference reference = db.collection("usuarios").document(email);
                                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            if(snapshot.exists()){
                                                int tipoUsuario = ((Long) snapshot.getData().get("tipo")).intValue();

                                                if(tipoUsuario == 1){
                                                    Intent intent = new Intent(LoginActivity.this, MainMentorActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                                if(tipoUsuario == 2){
                                                    Intent intent = new Intent(LoginActivity.this, MainMentoradoActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                            }

                                            Toast.makeText(LoginActivity.this, "Usuário logado!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginActivity.this, "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    Toast.makeText(LoginActivity.this, "Email ou senha inválido(s)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Log.i("Teste", e.getMessage());
                            }
                        });
            }
        });
    }

    private void checkSharedPreferences(){
        String checkBox = mSharedPreferences.getString(getString(R.string.checkbox),"False");
        String email = mSharedPreferences.getString(getString(R.string.email),"");
        String senha = mSharedPreferences.getString(getString(R.string.senha),"");

        edit_email.setText(email);
        edit_senha.setText(senha);

        if(checkBox.equals("True")){
            mCheckBox.setChecked(true);
        }else{
            mCheckBox.setChecked(false);
        }
    }
}