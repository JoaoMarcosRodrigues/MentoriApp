package com.example.mentoriapp.Mentorado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.MainMentorActivity;
import com.example.mentoriapp.MainMentoradoActivity;
import com.example.mentoriapp.Mentor.CadastroMentorActivity;
import com.example.mentoriapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class CadastroMentoradoActivity extends AppCompatActivity {

    private TextInputEditText mEditNomeMentorado, mEditEmailMentorado, mEditTelefoneMentorado, mEditSenhaMentorado, mEditAreaMentorado;
    private Button mBtnCadastroMentorado, mBtnFotoMentorado;
    private Uri mSelectedUri = null;
    private ImageView mImgPhoto;
    private Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mentorado);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastro do Mentorado");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        mBtnFotoMentorado = findViewById(R.id.btn_img_cadastro_mentorado);
        mEditNomeMentorado = findViewById(R.id.edit_nome_cadastro);
        mEditEmailMentorado = findViewById(R.id.edit_email_cadastro);
        mEditSenhaMentorado = findViewById(R.id.edit_senha_cadastro);
        mEditTelefoneMentorado = findViewById(R.id.edit_telefone_cadastro);
        mEditAreaMentorado = findViewById(R.id.edit_area_interesse_cadastro);
        mBtnCadastroMentorado = findViewById(R.id.btn_cadastrar);
        mImgPhoto = findViewById(R.id.img_photo);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(mEditTelefoneMentorado, smf);
        mEditTelefoneMentorado.addTextChangedListener(mtw);

        mBtnCadastroMentorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarMentorado();
            }
        });

        mBtnFotoMentorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPhoto();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            mSelectedUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mSelectedUri);
                mImgPhoto.setImageDrawable(new BitmapDrawable(bitmap));
                mBtnFotoMentorado.setAlpha(0);
            } catch (IOException e) {}
        }
    }

    private void selectedPhoto() {
        Intent  intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    private void criarMentorado() {
        String email = mEditEmailMentorado.getText().toString();
        String senha = mEditSenhaMentorado.getText().toString();
        String nome = mEditNomeMentorado.getText().toString();
        String telefone = mEditTelefoneMentorado.getText().toString();
        String areaAtuacao = mEditAreaMentorado.getText().toString();

        if(nome == null || nome.isEmpty() || email == null || email.isEmpty() ||
           senha == null || senha.isEmpty() || areaAtuacao == null || areaAtuacao.isEmpty()) {
            Toast.makeText(this,"Nome Completo, Email, Senha e Área de atuação devem ser preenchidos!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(senha.length() < 6)
            Toast.makeText(getApplicationContext(),"Senha deve conter no mínimo 6 caracteres!",Toast.LENGTH_SHORT).show();

        progressDialog.setMessage("Cadastrando mentorado...");
        progressDialog.show();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i("Teste", task.getResult().getUser().getUid());
                            saveMentoradoInFirebase();
                            saveUserInFirebase();
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(),"Mentorado cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        if(e.getMessage().equals("The email address is badly formatted."))
                            Toast.makeText(getApplicationContext(),"Email inválido! Tente novamente.",Toast.LENGTH_SHORT).show();

                        Log.i("Teste",e.getMessage());
                    }
                });
    }

    private void saveMentoradoInFirebase() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("mentorado/"+filename);
        if(mSelectedUri == null){
            Toast.makeText(this,"Selecione uma foto de perfil",Toast.LENGTH_SHORT).show();
            return;
        }
        ref.putFile(mSelectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Teste",uri.toString());

                                String uid = FirebaseAuth.getInstance().getUid();
                                String nome = mEditNomeMentorado.getText().toString();
                                String email = mEditEmailMentorado.getText().toString();
                                String senha = mEditSenhaMentorado.getText().toString();
                                String telefone = mEditTelefoneMentorado.getText().toString();
                                String areaAtuacao = mEditAreaMentorado.getText().toString();
                                String profileUrl = uri.toString();
                                String emailMentor = "";

                                Mentorado mentorado = new Mentorado(uid,email,emailMentor,senha,nome,areaAtuacao,telefone,profileUrl,2);
                                FirebaseFirestore.getInstance().collection("mentorados")
                                        .document(uid)
                                        .set(mentorado)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Toast.makeText(getApplicationContext(),"Mentorado cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CadastroMentoradoActivity.this, MainMentoradoActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Ops, houve um erro no cadastro! Tente novamente.",Toast.LENGTH_SHORT).show();
                                                Log.i("Teste",e.getMessage());
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste",e.getMessage(),e);
                    }
                });
    }

    private void saveUserInFirebase() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("usuarios/"+filename);
        if(mSelectedUri == null){
            Toast.makeText(this,"Selecione uma foto de perfil",Toast.LENGTH_SHORT).show();
            return;
        }
        ref.putFile(mSelectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //Log.i("Teste",uri.toString());

                                String uid = FirebaseAuth.getInstance().getUid();
                                String nome = mEditNomeMentorado.getText().toString();
                                String email = mEditEmailMentorado.getText().toString();
                                String senha = mEditSenhaMentorado.getText().toString();
                                String telefone = mEditTelefoneMentorado.getText().toString();
                                String areaAtuacao = mEditAreaMentorado.getText().toString();
                                String profileUrl = uri.toString();
                                Intent intent = getIntent();
                                int tipo = intent.getIntExtra("tipoUsuario",1);

                                Usuario usuario = new Usuario(uid,email,nome,telefone,profileUrl,areaAtuacao,senha,"",true,tipo);
                                FirebaseFirestore.getInstance().collection("usuarios")
                                        .document(uid)
                                        .set(usuario)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Ops, houve um erro no cadastro! Tente novamente.",Toast.LENGTH_SHORT).show();
                                                Log.i("Teste",e.getMessage());
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste",e.getMessage(),e);
                    }
                });
    }
}