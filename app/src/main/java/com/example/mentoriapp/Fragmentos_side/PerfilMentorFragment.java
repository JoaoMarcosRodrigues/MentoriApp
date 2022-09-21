package com.example.mentoriapp.Fragmentos_side;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilMentorFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    private Uri mSelectedUri = null;
    private CircleImageView fotoPerfil;
    private Spinner spinnerTempoAtuacao;
    private Button btnSalvarPerfil;
    private ImageButton btnFotoPerfil;
    private TextInputEditText editTelefone,editNome,editFormacao,editArea,editCurriculo;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mentor_perfil, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getContext());

        btnFotoPerfil = view.findViewById(R.id.btn_img_perfil);
        btnSalvarPerfil = view.findViewById(R.id.btn_salvar_perfil_mentor);
        editNome = view.findViewById(R.id.edit_nome_perfil_mentor);
        editTelefone = view.findViewById(R.id.edit_telefone_perfil_mentor);
        editFormacao = view.findViewById(R.id.edit_formacao_perfil_mentor);
        editArea = view.findViewById(R.id.edit_area_perfil_mentor);
        editCurriculo = view.findViewById(R.id.edit_curriculo_perfil_mentor);
        fotoPerfil = view.findViewById(R.id.img_photo_perfil);
        spinnerTempoAtuacao = view.findViewById(R.id.spinnerTempoExperiencia);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),R.array.tempoAtuacao, android.R.layout.simple_spinner_item);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTelefone, smf);
        editTelefone.addTextChangedListener(mtw);

        editNome.setText(firebaseUser.getDisplayName());

        btnFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPhoto();
            }
        });

        btnSalvarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPerfil();
            }
        });

        spinnerTempoAtuacao.setAdapter(adapterSpinner);

        if(firebaseUser != null) {
            atualizarPerfil();
        }

        return view;
    }

    private void salvarPerfil() {
        String nome = editNome.getText().toString();
        String telefone = editTelefone.getText().toString();
        String formacao = editFormacao.getText().toString();
        String area = editArea.getText().toString();
        String curriculo = editCurriculo.getText().toString();
        String tempoAtuacao = spinnerTempoAtuacao.getSelectedItem().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(nome.isEmpty() || telefone.isEmpty() || formacao.isEmpty() || area.isEmpty() || curriculo.isEmpty() || tempoAtuacao.isEmpty()){
            builder.setTitle("Campos obrigatórios")
                    .setMessage("Todos os campos são obrigatórios!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            builder.create();
            builder.show();
            //Toast.makeText(getContext(),"O(s) campo(s) não pode(m) ficar vazio(s)!",Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressDialog.setMessage("Atualizando perfil...");
            progressDialog.show();

            String filename = UUID.randomUUID().toString();
            final StorageReference ref = FirebaseStorage.getInstance().getReference("mentor/"+filename);
            if(mSelectedUri != null){
                ref.putFile(mSelectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String profileUrl = uri.toString();

                                firebaseFirestore.collection("mentores").document(firebaseUser.getUid())
                                        .update("nome", nome, "telefone", telefone, "formacao", formacao, "areaAtuacao", area, "curriculo", curriculo, "tempoAtuacao", tempoAtuacao,"profileUrl",profileUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //progressDialog.dismiss();
                                                //Toast.makeText(getContext(),"Perfil atualizado!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                firebaseFirestore.collection("usuarios").document(firebaseUser.getUid())
                                        .update("nome", nome, "telefone", telefone, "areaAtuacao", area,"photoUrl",profileUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                builder.setTitle("Atualização do perfil")
                                                        .setMessage("Perfil atualizado!")
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                            }
                                                        });

                                                builder.create();
                                                builder.show();
                                                //Toast.makeText(getContext(),"Perfil atualizado!",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                builder.setTitle("Atualização do perfil")
                                                        .setMessage("Ops, houve um erro ao atualizar seu perfil. Tente novamente.")
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                            }
                                                        });

                                                builder.create();
                                                builder.show();
                                                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage(), e);
                    }
                });
            }else{
                firebaseFirestore.collection("mentores").document(firebaseUser.getUid())
                        .update("nome", nome, "telefone", telefone, "formacao", formacao, "areaAtuacao", area, "curriculo", curriculo, "tempoAtuacao", tempoAtuacao)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //progressDialog.dismiss();
                                //Toast.makeText(getContext(),"Perfil atualizado!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                firebaseFirestore.collection("usuarios").document(firebaseUser.getUid())
                        .update("nome", nome, "telefone", telefone, "areaAtuacao", area)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                builder.setTitle("Atualização do perfil")
                                        .setMessage("Perfil atualizado!")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                builder.create();
                                builder.show();
                                //Toast.makeText(getContext(),"Perfil atualizado!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                builder.setTitle("Atualização do perfil")
                                        .setMessage("Ops, houve um erro ao atualizar seu perfil. Tente novamente.")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                builder.create();
                                builder.show();
                                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private void atualizarPerfil() {
        TextView nomePerfil = view.findViewById(R.id.txt_nome_perfil_mentor);
        TextView areaAtuacao = view.findViewById(R.id.txt_area_atuacao_perfil_mentor);
        TextView email = view.findViewById(R.id.txt_email_perfil_mentor);
        Spinner spinnerTempoAtuacao = view.findViewById(R.id.spinnerTempoExperiencia);
        //TextView tempoAtuacao = view.findViewById(R.id.txt_tempo_experiencia);

        email.setText(firebaseUser.getEmail());

        firebaseFirestore.collection("mentores").whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> listaTempoAtuacao = Arrays.asList(getResources().getStringArray(R.array.tempoAtuacao));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int index = listaTempoAtuacao.indexOf(document.getData().get("tempoAtuacao").toString());
                                nomePerfil.setText(document.getData().get("nome").toString());
                                areaAtuacao.setText("Área de atuação: " + document.getData().get("areaAtuacao").toString());
                                //tempoAtuacao.setText(document.getData().get("tempoAtuacao").toString());
                                spinnerTempoAtuacao.setSelection(index);
                                editNome.setText(document.getData().get("nome").toString());
                                editTelefone.setText(document.getData().get("telefone").toString());
                                editFormacao.setText(document.getData().get("formacao").toString());
                                editArea.setText(document.getData().get("areaAtuacao").toString());
                                editCurriculo.setText(document.getData().get("curriculo").toString());
                                Picasso.get().load(document.getData().get("profileUrl").toString()).placeholder(R.drawable.ic_launcher_background).into(fotoPerfil);
                            }
                        } else {
                            Log.d("mentores", "Error: " + task.getException());
                        }
                    }
                });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            mSelectedUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),mSelectedUri);
                fotoPerfil.setImageDrawable(new BitmapDrawable(bitmap));
                //btnFotoPerfil.setAlpha(0);
            } catch (IOException e) {}
        }
    }

    private void selectedPhoto() {
        Intent  intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
}