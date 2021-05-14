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
import com.example.mentoriapp.Classes.Avaliacao;
import com.example.mentoriapp.Listas.ListaAprendizadosFragment;
import com.example.mentoriapp.Listas.ListaAvaliacoesFragment;
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

public class CadastroAvaliacaoFragment extends Fragment {

    View view;
    TextInputEditText editDescricao,editTitulo;
    Button btnCadastroAvaliacao;
    Spinner spinnerRelatos;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    int maxid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_avaliacao, container, false);

        editTitulo = view.findViewById(R.id.edit_titulo_avaliacao);
        editDescricao = view.findViewById(R.id.edit_descricao_avaliacao);
        spinnerRelatos = view.findViewById(R.id.spinner_relatos);
        btnCadastroAvaliacao = view.findViewById(R.id.btn_cadastrar_avaliacao);
        progressDialog = new ProgressDialog(getContext());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("avaliacoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        btnCadastroAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAvaliacao();
            }
        });

        return view;
    }

    private void cadastrarAvaliacao() {
        String titulo = editTitulo.getText().toString();
        String descricao = editDescricao.getText().toString();
        String emailMentorado = user.getEmail();
        int id_relato = 1;

        if(descricao == null || descricao.isEmpty()){
            Toast.makeText(getContext(),"Descrição obrigatória!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando avaliação...");
        progressDialog.show();

        Avaliacao avaliacao = new Avaliacao(maxid,id_relato,titulo,descricao,emailMentorado);

        FirebaseFirestore.getInstance().collection("avaliacoes")
                .add(avaliacao)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(),"Avaliação cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentor, new ListaAvaliacoesFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro no cadastro da avaliação! Tente novamente.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}