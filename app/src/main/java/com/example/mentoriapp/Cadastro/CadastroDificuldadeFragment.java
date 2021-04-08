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

import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.Listas.ListaRelatosFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroDificuldadeFragment extends Fragment {

    TextInputEditText editTag, editDescricao;
    ProgressDialog progressDialog;
    Button btnSalvar;

    public static CadastroDificuldadeFragment getInstance(){
        CadastroDificuldadeFragment cadastroDificuldadeFragment = new CadastroDificuldadeFragment();
        return cadastroDificuldadeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_dificuldade, container, false);

        editTag = view.findViewById(R.id.edit_tag_dificuldade);
        editDescricao = view.findViewById(R.id.edit_dificuldade);
        progressDialog = new ProgressDialog(getContext());
        btnSalvar = view.findViewById(R.id.btn_cadastrar_dificuldade);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarDificuldade();
            }
        });


        return view;
    }

    private void cadastrarDificuldade() {
        String tag = editTag.getText().toString();
        String descricao = editDescricao.getText().toString();

        if(tag == null || tag.isEmpty() || descricao.isEmpty() || descricao == null){
            Toast.makeText(getContext(),"Tag e descrição obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando dificuldade...");
        progressDialog.show();

        Dificuldade dificuldade = new Dificuldade();
        dificuldade.setId(1);
        dificuldade.setIdRelato(1);
        dificuldade.setTagDificuldade(tag);
        dificuldade.setDescricaoDificuldade(descricao);

        FirebaseFirestore.getInstance().collection("dificuldades")
                .add(dificuldade)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Dificuldade cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                        editTag.setEnabled(false);
                        editDescricao.setEnabled(false);
                        btnSalvar.setEnabled(false);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment,new ListaRelatosFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro no cadastro da dificuldade! Tente novamente.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}