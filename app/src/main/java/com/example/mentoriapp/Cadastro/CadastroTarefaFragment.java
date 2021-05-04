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
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.Listas.ListaTarefasFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroTarefaFragment extends Fragment {

    TextInputEditText descricaoTarefa;
    TextInputEditText tituloTarefa;
    Button btnCadastroTarefa;
    ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_aprendizado, container, false);

        descricaoTarefa = view.findViewById(R.id.edit_descricao);
        btnCadastroTarefa = view.findViewById(R.id.btn_cadastrar_tarefa);
        tituloTarefa = view.findViewById(R.id.edit_titulo);
        progressDialog = new ProgressDialog(getContext());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnCadastroTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarTarefa();
            }
        });

        return view;
    }

    private void cadastrarTarefa() {
        boolean status = false;
        String titulo = tituloTarefa.getText().toString();
        String descricao = descricaoTarefa.getText().toString();
        String emailMentor = user.getEmail();

        if(titulo == null || titulo.isEmpty() || descricao == null || descricao.isEmpty()){
            Toast.makeText(getContext(),"Campo título e descrição obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando tarefa...");
        progressDialog.show();


        Tarefa tarefa = new Tarefa(1,titulo,descricao,emailMentor,status);

        FirebaseFirestore.getInstance().collection("tarefas")
                .add(tarefa)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Tarefa cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentor, new ListaTarefasFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro no cadastro da tarefa! Tente novamente.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}