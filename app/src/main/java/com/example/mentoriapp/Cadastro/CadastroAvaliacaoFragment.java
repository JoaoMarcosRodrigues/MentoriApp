package com.example.mentoriapp.Cadastro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputLayout;
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

public class CadastroAvaliacaoFragment extends Fragment {

    View view;
    TextInputEditText editDescricao,editTitulo;
    TextInputEditText layout_titulo_avaliacao,layout_descricao_avaliacao;
    Button btnCadastroAvaliacao;
    Spinner spinnerRelatos;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private CollectionReference ref;
    private FirebaseFirestore mFirestore;
    String mentoradoSelecionado;
    int maxid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_avaliacao, container, false);

        editTitulo = view.findViewById(R.id.edit_titulo_avaliacao);
        editDescricao = view.findViewById(R.id.edit_descricao_avaliacao);
        spinnerRelatos = view.findViewById(R.id.spinner_relatos);
        btnCadastroAvaliacao = view.findViewById(R.id.btn_cadastrar_avaliacao);
        layout_titulo_avaliacao = view.findViewById(R.id.layout_titulo_avaliacao);
        layout_descricao_avaliacao = view.findViewById(R.id.layout_descricao_avaliacao);
        progressDialog = new ProgressDialog(getContext());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        ref = mFirestore.collection("mentorias");
        List<String> listaMentorados = new ArrayList<>();
        listaMentorados.add("Selecione...");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,listaMentorados){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelatos.setAdapter(arrayAdapter);

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

        spinnerRelatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mentoradoSelecionado = spinnerRelatos.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Quando nada for selecionado
            }
        });

        mFirestore.collection("mentores").document(user.getUid()).collection("avaliacoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(titulo.isEmpty()){
            layout_titulo_avaliacao.setError("Título obrigatório!");
        }else{
            layout_titulo_avaliacao.setError("");
        }

        if(descricao.isEmpty()){
            layout_descricao_avaliacao.setError("Descrição obrigatória!");
        }else{
            layout_descricao_avaliacao.setError("");
        }

        if(mentoradoSelecionado.equals("Selecione...")){
            Toast.makeText(getContext(),"Selecione um mentorado para continuar.",Toast.LENGTH_SHORT).show();
        }

        if(!titulo.isEmpty() && !descricao.isEmpty() && !mentoradoSelecionado.equals("Selecione...")){
            progressDialog.setMessage("Cadastrando avaliação...");
            progressDialog.show();

            Avaliacao avaliacao = new Avaliacao(maxid,mentoradoSelecionado,titulo,descricao,emailMentorado);

            FirebaseFirestore.getInstance().collection("mentores").document(user.getUid()).collection("avaliacoes")
                    .add(avaliacao)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro de avaliação")
                                    .setMessage("Avaliação cadastrada com sucesso!")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Avaliação cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentor, new ListaAvaliacoesFragment()).addToBackStack(null)
                                    .commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro de avaliação")
                                    .setMessage("Avaliação cadastrada com sucesso!")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Ops, houve um erro no cadastro da avaliação! Tente novamente.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}