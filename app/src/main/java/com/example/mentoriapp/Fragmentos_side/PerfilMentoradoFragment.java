package com.example.mentoriapp.Fragmentos_side;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilMentoradoFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    private Uri mSelectedUri = null;
    private CircleImageView fotoPerfil;
    private Button btnSalvarPerfil;
    private ImageButton btnFotoPerfil;
    private TextInputEditText editTelefone,editNome,editArea;
    //private String profileUrl;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mentorado_perfil, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getContext());

        btnFotoPerfil = view.findViewById(R.id.btn_img_perfil);
        btnSalvarPerfil = view.findViewById(R.id.btn_salvar_perfil_mentorado);
        editNome = view.findViewById(R.id.edit_nome_perfil_mentorado);
        editTelefone = view.findViewById(R.id.edit_telefone_perfil_mentorado);
        editArea = view.findViewById(R.id.edit_area_perfil_mentorado);
        fotoPerfil = view.findViewById(R.id.img_photo_perfil);

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

        if(firebaseUser != null) {
            atualizarPerfil();
        }


        return view;
    }

    private void atualizarPerfil() {
        TextView nomePerfil = view.findViewById(R.id.txt_nome_perfil_mentorado);
        TextView email = view.findViewById(R.id.txt_email_perfil_mentorado);
        email.setText(firebaseUser.getEmail());

        firebaseFirestore.collection("mentorados").whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nomePerfil.setText(document.getData().get("nome").toString());
                                editNome.setText(document.getData().get("nome").toString());
                                editTelefone.setText(document.getData().get("telefone").toString());
                                editArea.setText(document.getData().get("areaAtuacao").toString());
                                Picasso.get().load(document.getData().get("profileUrl").toString()).placeholder(R.drawable.ic_launcher_background).into(fotoPerfil);
                            }
                        } else {
                            Log.d("mentorados", "Error: " + task.getException());
                        }
                    }
                });
    }

    private void salvarPerfil() {
        String nome = editNome.getText().toString();
        String telefone = editTelefone.getText().toString();
        String area = editArea.getText().toString();
        String email = firebaseUser.getEmail();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(nome.isEmpty() || telefone.isEmpty() || area.isEmpty()){
            builder.setTitle("Campos obrigatórios")
                    .setMessage("Todos os campos são obrigatórios!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            builder.create();
            builder.show();
            //Toast.makeText(getContext(),"Email ou Telefone vazio(s)!.",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.setMessage("Atualizando perfil...");
            progressDialog.show();

            String filename = UUID.randomUUID().toString();
            final StorageReference ref = FirebaseStorage.getInstance().getReference("mentorado/"+filename);
            if(mSelectedUri != null){
                ref.putFile(mSelectedUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String profileUrl = uri.toString();

                                        firebaseFirestore.collection("mentorados").document(firebaseUser.getUid())
                                                .update("nome", nome, "telefone", telefone, "areaAtuacao", area, "profileUrl", profileUrl)
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
                                                .update("nome", nome, "telefone", telefone, "areaAtuacao", area, "photoUrl", profileUrl)
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
                                                        //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                firebaseFirestore.collection("mentorados").document(firebaseUser.getUid())
                        .update("nome", nome, "telefone", telefone, "areaAtuacao", area)
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
                                //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
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