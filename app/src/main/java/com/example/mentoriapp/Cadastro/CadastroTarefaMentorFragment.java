package com.example.mentoriapp.Cadastro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.Listas.ListaTarefasMentorFragment;
import com.example.mentoriapp.R;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CadastroTarefaMentorFragment extends Fragment {

    private TextInputEditText descricaoTarefa;
    private TextInputEditText tituloTarefa;
    private Button btnCadastroTarefa;
    private ProgressDialog progressDialog;
    private Spinner spinnerMentorado;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private CollectionReference ref;
    String mentoradoSelecionado;
    int maxid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_tarefa_mentor, container, false);

        descricaoTarefa = view.findViewById(R.id.edit_descricao_tarefa);
        btnCadastroTarefa = view.findViewById(R.id.btn_cadastrar_tarefa);
        tituloTarefa = view.findViewById(R.id.edit_titulo_tarefa);
        spinnerMentorado = view.findViewById(R.id.spinner_mentorados);
        progressDialog = new ProgressDialog(getContext());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("tarefas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        //ref = mFirestore.collection("mentorias").document(user.getUid()).collection("relatos");
        ref = mFirestore.collection("mentorias");
        List<String> listaMentorados = new ArrayList<>();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,listaMentorados);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMentorado.setAdapter(arrayAdapter);

        ref.whereEqualTo("emailMentor",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("emailMentorado");
                        listaMentorados.add(subject);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        spinnerMentorado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mentoradoSelecionado = spinnerMentorado.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Quando nada for selecionado
            }
        });

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
        String email = user.getEmail();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(titulo == null || titulo.isEmpty() || descricao == null || descricao.isEmpty()){
            builder.setTitle("Campos obrigatórios")
                    .setMessage("Todos os campos são obrigatórios!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            builder.create();
            builder.show();
            //Toast.makeText(getContext(),"Campo título e descrição obrigatórios!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Cadastrando tarefa...");
        progressDialog.show();


        Tarefa tarefa = new Tarefa(maxid,titulo,descricao,email,mentoradoSelecionado,status);

        FirebaseFirestore.getInstance().collection("tarefas")
                .document(Integer.toString(maxid))
                .set(tarefa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        builder.setTitle("Cadastro da tarefa")
                                .setMessage("Tarefa cadastrada!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        builder.create();
                        builder.show();
                        //Toast.makeText(getContext(),"Tarefa cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentor, new ListaTarefasMentorFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        builder.setTitle("Cadastro da tarefa")
                                .setMessage("Ops, houve um erro no cadastro da tarefa! Tente novamente.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        builder.create();
                        builder.show();
                        //Toast.makeText(getContext(),"Ops, houve um erro no cadastro da tarefa! Tente novamente.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}