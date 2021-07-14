package com.example.mentoriapp.Mentor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.MainMentorActivity;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class CadastroMentorActivity extends AppCompatActivity {

    private TextInputEditText edit_email,edit_nome,edit_telefone,edit_senha,edit_area_atuacao,edit_formacao,edit_curriculo;
    private Button botao_next, mBtnFotoMentor;
    private ImageView mImgPhoto;
    private Toolbar toolbar;
    private Uri mSelectedUri = null;
    private Spinner spinnerTempoAtuacao;
    private ProgressDialog progressDialog;
    private String tempoAtuacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mentor);

        toolbar = findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastro do Mentor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        botao_next = findViewById(R.id.btn_next);
        edit_email = findViewById(R.id.edit_email_cadastro);
        edit_nome = findViewById(R.id.edit_nome_cadastro);
        edit_telefone = findViewById(R.id.edit_telefone_cadastro);
        edit_senha = findViewById(R.id.edit_senha_cadastro);
        edit_area_atuacao = findViewById(R.id.edit_atuacao_cadastro);
        edit_formacao = findViewById(R.id.edit_formacao);
        edit_curriculo = findViewById(R.id.edit_curriculo);
        mBtnFotoMentor = findViewById(R.id.btn_img_cadastro_mentor);
        spinnerTempoAtuacao = findViewById(R.id.spinnerTempoExperiencia);
        mImgPhoto = findViewById(R.id.img_photo);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.tempoAtuacao, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTempoAtuacao.setAdapter(adapterSpinner);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edit_telefone, smf);
        edit_telefone.addTextChangedListener(mtw);

        spinnerTempoAtuacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempoAtuacao = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botao_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroMentor();
            }
        });

        mBtnFotoMentor.setOnClickListener(new View.OnClickListener() {
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
                mBtnFotoMentor.setAlpha(0);
            } catch (IOException e) {}
        }
    }

    private void selectedPhoto() {
        Intent  intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }


    private void cadastroMentor() {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();
        String nome = edit_nome.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String area_atuacao = edit_area_atuacao.getText().toString();
        String formacao = edit_formacao.getText().toString();
        String curriculo = edit_curriculo.getText().toString();

        if(nome == null || nome.isEmpty() || email == null || email.isEmpty() ||
                senha == null || senha.isEmpty() || area_atuacao == null || area_atuacao.isEmpty()
                || formacao == null || formacao.isEmpty() || curriculo == null || curriculo.isEmpty()) {
            Toast.makeText(this,"Nome Completo, Email, Área de atuação, Formação, Currículo e Senha devem ser preenchidos!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(senha.length() < 6)
            Toast.makeText(getApplicationContext(),"Senha deve conter no mínimo 6 caracteres!",Toast.LENGTH_SHORT).show();

        progressDialog.setMessage("Cadastrando mentor...");
        progressDialog.show();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i("Teste", task.getResult().getUser().getUid());
                            saveMentorInFirebase();
                            saveUserInFirebase();
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(),"Mentor cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
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

    private void saveMentorInFirebase() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("mentor/"+filename);
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
                                String nome = edit_nome.getText().toString();
                                String email = edit_email.getText().toString();
                                String senha = edit_senha.getText().toString();
                                String telefone = edit_telefone.getText().toString();
                                String area_atuacao = edit_area_atuacao.getText().toString();
                                String formacao = edit_formacao.getText().toString();
                                String curriculo = edit_curriculo.getText().toString();
                                String profileUrl = uri.toString();

                                Mentor mentor = new Mentor(uid,email,area_atuacao,tempoAtuacao,senha,nome,telefone,profileUrl,formacao,curriculo,1);
                                FirebaseFirestore.getInstance().collection("mentores")
                                        .document(uid)
                                        .set(mentor)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Toast.makeText(getApplicationContext(),"Mentor cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CadastroMentorActivity.this, MainMentorActivity.class);
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
                                String nome = edit_nome.getText().toString();
                                String email = edit_email.getText().toString();
                                String senha = edit_senha.getText().toString();
                                String telefone = edit_telefone.getText().toString();
                                String areaAtuacao = edit_area_atuacao.getText().toString();
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