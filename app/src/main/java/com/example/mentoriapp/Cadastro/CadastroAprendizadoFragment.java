package com.example.mentoriapp.Cadastro;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroAprendizadoFragment extends Fragment {

    TextInputEditText descricaoAprendizado;
    Button btnCadastroAprendizado;
    ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(getContext());

        btnCadastroAprendizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAprendizado();
            }
        });

        return view;
    }

    private void cadastrarAprendizado() {
        String descricao = descricaoAprendizado.getText().toString();
        Bundle bundle = new Bundle();
        int id_relato = bundle.getInt("idRelato");

        if(descricao == null || descricao.isEmpty()){
            Toast.makeText(getContext(),"Campo descrição obrigatório!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando aprendizado...");
        progressDialog.show();


        Aprendizado aprendizado = new Aprendizado(1,id_relato,descricao);

        FirebaseFirestore.getInstance().collection("aprendizados")
                .add(aprendizado)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Aprendizado cadasrado com sucesso!",Toast.LENGTH_SHORT).show();
                        descricaoAprendizado.setEnabled(false);
                        btnCadastroAprendizado.setEnabled(false);
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