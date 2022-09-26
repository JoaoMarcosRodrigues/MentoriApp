package com.example.mentoriapp.Cadastro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.Listas.ListaTarefasMentorFragment;
import com.example.mentoriapp.Listas.ListaTarefasMentoradoFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CadastroTarefaMentoradoFragment extends Fragment {

    private TextInputEditText descricaoTarefa,tituloTarefa;
    private TextInputLayout inputDescricao,inputTitulo;
    private Button btnCadastroTarefa;
    ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    int maxid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_tarefa_mentorado, container, false);

        descricaoTarefa = view.findViewById(R.id.edit_descricao_tarefa);
        btnCadastroTarefa = view.findViewById(R.id.btn_cadastrar_tarefa);
        inputTitulo = view.findViewById(R.id.layout_titulo_tarefa);
        inputDescricao = view.findViewById(R.id.layout_descricao_tarefa);
        tituloTarefa = view.findViewById(R.id.edit_titulo_tarefa);
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

        if(titulo.isEmpty()){
            inputTitulo.setError("Título obrigatório!");
        }else{
            inputTitulo.setError("");
        }

        if(descricao.isEmpty()){
            inputDescricao.setError("Descrição obrigatória!");
        }else{
            inputDescricao.setError("");
        }

        if(!titulo.isEmpty() && !descricao.isEmpty()){
            progressDialog.setMessage("Cadastrando tarefa...");
            progressDialog.show();

            Tarefa tarefa = new Tarefa(maxid,titulo,descricao,email,email,status);

            FirebaseFirestore.getInstance().collection("tarefas")
                    .document(Integer.toString(maxid))
                    .set(tarefa)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
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
                                    .replace(R.id.fragment_mentorado, new ListaTarefasMentoradoFragment())
                                    .addToBackStack(null)
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
}