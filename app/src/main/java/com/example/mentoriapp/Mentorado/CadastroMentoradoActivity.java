package com.example.mentoriapp.Mentorado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroMentoradoActivity extends AppCompatActivity {

    ImageView mImgagemMentorado;
    TextInputEditText mEditNomeMentorado, mEditEmailMentorado, mEditTelefoneMentorado, mEditSenhaMentorado, mEditAreaMentorado;
    Button mBtnCadastroMentorado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mentorado);

        mImgagemMentorado = findViewById(R.id.img_cadastro_mentorado);
        mEditNomeMentorado = findViewById(R.id.edit_nome_cadastro);
        mEditEmailMentorado = findViewById(R.id.edit_email_cadastro);
        mEditSenhaMentorado = findViewById(R.id.edit_senha_cadastro);
        mEditTelefoneMentorado = findViewById(R.id.edit_telefone_cadastro);
        mEditAreaMentorado = findViewById(R.id.edit_area_interesse_cadastro);
        mBtnCadastroMentorado = findViewById(R.id.btn_cadastrar);

        mBtnCadastroMentorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarMentorado();
            }
        });

    }

    private void criarMentorado() {
        String email = mEditEmailMentorado.getText().toString();
        String senha = mEditSenhaMentorado.getText().toString();

        if(email == null || email.isEmpty() || senha.isEmpty() || senha == null){
            Toast.makeText(this,"Email e senha devem ser preenchidos",Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                            Log.i("Teste",task.getResult().getUser().getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste",e.getMessage());
                    }
                });
    }
}