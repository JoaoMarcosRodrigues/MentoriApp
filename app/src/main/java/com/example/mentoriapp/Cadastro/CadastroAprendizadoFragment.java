package com.example.mentoriapp.Cadastro;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Listas.ListaAprendizadosFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CadastroAprendizadoFragment extends Fragment {

    TextInputEditText descricaoAprendizado;
    TextInputEditText tituloAprendizado;
    Button btnCadastroAprendizado;
    ProgressDialog progressDialog;
    Spinner spinnerRelatos;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    int maxid;

    public static CadastroAprendizadoFragment getInstance(){
        CadastroAprendizadoFragment cadastroAprendizadoFragment = new CadastroAprendizadoFragment();
        return cadastroAprendizadoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_aprendizado, container, false);

        descricaoAprendizado = view.findViewById(R.id.edit_descricao_aprendizado);
        btnCadastroAprendizado = view.findViewById(R.id.btn_cadastrar_aprendizado);
        tituloAprendizado = view.findViewById(R.id.edit_titulo_aprendizado);
        progressDialog = new ProgressDialog(getContext());
        spinnerRelatos = view.findViewById(R.id.spinner_relatos);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("mentorados").document(user.getUid()).collection("aprendizados").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    maxid = 1;
                    for(DocumentSnapshot document : task.getResult()){
                        maxid++;
                    }
                }
            }
        });

        btnCadastroAprendizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAprendizado();
            }
        });

        return view;
    }

    private void cadastrarAprendizado() {
        String titulo = tituloAprendizado.getText().toString();
        String descricao = descricaoAprendizado.getText().toString();
        String emailMentorado = user.getEmail();
        Bundle bundle = new Bundle();
        int id_relato = bundle.getInt("idRelato");

        if(titulo == null || titulo.isEmpty() || descricao == null || descricao.isEmpty()){
            Toast.makeText(getContext(),"Campo título e descrição obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando aprendizado...");
        progressDialog.show();

        Aprendizado aprendizado = new Aprendizado(maxid,id_relato,titulo,descricao,emailMentorado);

        FirebaseFirestore.getInstance().collection("mentorados").document(user.getUid()).collection("aprendizados")
                .add(aprendizado)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt("idRelato",id_relato);
                        Toast.makeText(getContext(),"Aprendizado cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        //descricaoAprendizado.setEnabled(false);
                        //btnCadastroAprendizado.setEnabled(false);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentorado, new ListaAprendizadosFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro no cadastro do aprendizado! Tente novamente.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}