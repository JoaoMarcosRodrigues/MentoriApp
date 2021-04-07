package com.example.mentoriapp.Fragmentos_side;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PerfilMentoradoFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mentorado_perfil, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        TextView nomePerfil = view.findViewById(R.id.txt_nome_perfil_mentorado);
        TextView areaAtuacao = view.findViewById(R.id.txt_area_atuacao_perfil_mentorado);
        ImageView fotoPerfil = view.findViewById(R.id.img_perfil_mentorado);
        TextInputEditText editEmail = view.findViewById(R.id.edit_email_perfil_mentorado);
        TextInputEditText editTelefone = view.findViewById(R.id.edit_telefone_perfil_mentorado);

        nomePerfil.setText(firebaseUser.getDisplayName());
        Picasso.get().load(firebaseUser.getPhotoUrl()).into(fotoPerfil);
        editEmail.setText(firebaseUser.getEmail());
        editTelefone.setText(firebaseUser.getPhoneNumber());

        firebaseFirestore.collection("mentorados").whereEqualTo("email",firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d("mentorados",document.getId()+ " => "+document.getData());
                                areaAtuacao.setText(document.getData().get("areaAtuacao").toString());
                            }
                        }else{
                            Log.d("mentorados","Error: "+task.getException());
                        }
                    }
                });


        return view;
    }
}