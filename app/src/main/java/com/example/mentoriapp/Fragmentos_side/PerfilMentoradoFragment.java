package com.example.mentoriapp.Fragmentos_side;

import android.app.ProgressDialog;
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
    private TextInputEditText editTelefone,editEmail;
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
        editEmail = view.findViewById(R.id.edit_email_perfil_mentorado);
        editTelefone = view.findViewById(R.id.edit_telefone_perfil_mentorado);
        fotoPerfil = view.findViewById(R.id.img_photo_perfil);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTelefone, smf);
        editTelefone.addTextChangedListener(mtw);

        editEmail.setText(firebaseUser.getEmail());

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
        TextView areaAtuacao = view.findViewById(R.id.txt_area_atuacao_perfil_mentorado);

        firebaseFirestore.collection("usuarios").whereEqualTo("email", firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nomePerfil.setText(document.getData().get("nome").toString());
                                areaAtuacao.setText("Área de atuação: " + document.getData().get("areaAtuacao").toString());
                                editTelefone.setText(document.getData().get("telefone").toString());
                                Picasso.get().load(document.getData().get("photoUrl").toString()).placeholder(R.drawable.ic_launcher_background).into(fotoPerfil);
                            }
                        } else {
                            Log.d("mentorados", "Error: " + task.getException());
                        }
                    }
                });
    }

    private void salvarPerfil() {
        String email = editEmail.getText().toString();
        String telefone = editTelefone.getText().toString();

        if(email.isEmpty() || telefone.isEmpty()){
            Toast.makeText(getContext(),"Email ou Telefone vazio(s)!.",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.setMessage("Atualizando perfil...");
            progressDialog.show();

            firebaseFirestore.collection("mentorados").document(firebaseUser.getUid())
                    .update("email", email, "telefone", telefone)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseUser.updateEmail(email);

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
                    .update("email", email, "telefone", telefone)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseUser.updateEmail(email);

                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Perfil atualizado!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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