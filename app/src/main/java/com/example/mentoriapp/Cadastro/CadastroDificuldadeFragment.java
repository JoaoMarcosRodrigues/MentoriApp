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

import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.Listas.ListaDificuldadesFragment;
import com.example.mentoriapp.Listas.ListaRelatosFragment;
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

public class CadastroDificuldadeFragment extends Fragment {

    TextInputEditText editTag, editDescricao;
    TextInputLayout inputTag,inputDescricao;
    ProgressDialog progressDialog;
    Button btnSalvar;
    Spinner spinnerRelatos;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private CollectionReference ref;
    int maxid;
    String relatoSelecionado;

    public static CadastroDificuldadeFragment getInstance(){
        CadastroDificuldadeFragment cadastroDificuldadeFragment = new CadastroDificuldadeFragment();
        return cadastroDificuldadeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_dificuldade, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        ref = mFirestore.collection("relatos");
        List<String> listaRelatos = new ArrayList<>();
        listaRelatos.add("Selecione...");

        editTag = view.findViewById(R.id.edit_tag_dificuldade);
        editDescricao = view.findViewById(R.id.edit_dificuldade);
        inputTag = view.findViewById(R.id.layout_tag_dificuldade);
        inputDescricao = view.findViewById(R.id.layout_dificuldade);
        progressDialog = new ProgressDialog(getContext());
        btnSalvar = view.findViewById(R.id.btn_cadastrar_dificuldade);
        spinnerRelatos = view.findViewById(R.id.spinner_relatos);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,listaRelatos){
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

        ref.whereEqualTo("emailMentorado",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("titulo");
                        listaRelatos.add(subject);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        spinnerRelatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relatoSelecionado = spinnerRelatos.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Quando nada for selecionado
            }
        });

        mFirestore.collection("mentorados").document(user.getUid()).collection("dificuldades").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(tag.isEmpty()){
            inputTag.setError("Tag obrigatório!");
        }else{
            inputTag.setError("");
        }

        if(descricao.isEmpty()){
            inputDescricao.setError("Descrição obrigatória!");
        }else{
            inputDescricao.setError("");
        }

        if(relatoSelecionado.equals("Selecione...")){
            Toast.makeText(getContext(),"Selecione um relato para continuar.",Toast.LENGTH_SHORT).show();
        }

        if(!tag.isEmpty() && !descricao.isEmpty() && !relatoSelecionado.equals("Selecione...")){
            progressDialog.setMessage("Cadastrando dificuldade...");
            progressDialog.show();

            String email = user.getEmail();

            Dificuldade dificuldade = new Dificuldade();
            dificuldade.setId(maxid);
            dificuldade.setTituloRelato(relatoSelecionado);
            dificuldade.setTagDificuldade(tag);
            dificuldade.setEmailMentorado(email);
            dificuldade.setFavorito(false);
            dificuldade.setDescricaoDificuldade(descricao);

            FirebaseFirestore.getInstance().collection("mentorados").document(user.getUid()).collection("dificuldades")
                    .add(dificuldade)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro da dificuldade")
                                    .setMessage("Dificuldade cadastrada com sucesso!")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Dificuldade cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_mentorado,new ListaDificuldadesFragment()).addToBackStack(null)
                                    .commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro da dificuldade")
                                    .setMessage("Ops, houve um erro no cadastro da dificuldade! Tente novamente.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Ops, houve um erro no cadastro da dificuldade! Tente novamente.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}