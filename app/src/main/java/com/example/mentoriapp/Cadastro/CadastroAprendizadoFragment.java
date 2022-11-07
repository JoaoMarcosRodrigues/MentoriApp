package com.example.mentoriapp.Cadastro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Listas.ListaAprendizadosFragment;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CadastroAprendizadoFragment extends Fragment {

    TextInputEditText descricaoAprendizado,tituloAprendizado;
    TextInputLayout layout_titulo_aprendizado,layout_descricao_aprendizado;
    Button btnCadastroAprendizado;
    ProgressDialog progressDialog;
    Spinner spinnerRelatos;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private CollectionReference ref;
    int maxid;
    String relatoSelecionado;

    public static CadastroAprendizadoFragment getInstance(){
        CadastroAprendizadoFragment cadastroAprendizadoFragment = new CadastroAprendizadoFragment();
        return cadastroAprendizadoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_aprendizado, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        ref = mFirestore.collection("relatos");
        List<String> listaRelatos = new ArrayList<>();
        listaRelatos.add("Selecione...");

        descricaoAprendizado = view.findViewById(R.id.edit_descricao_aprendizado);
        btnCadastroAprendizado = view.findViewById(R.id.btn_cadastrar_aprendizado);
        tituloAprendizado = view.findViewById(R.id.edit_titulo_aprendizado);
        layout_titulo_aprendizado = view.findViewById(R.id.layout_titulo_aprendizado);
        layout_descricao_aprendizado = view.findViewById(R.id.layout_descricao_aprendizado);
        progressDialog = new ProgressDialog(getContext());
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(titulo.isEmpty()){
            layout_titulo_aprendizado.setError("Título obrigatório!");
        }else{
            layout_titulo_aprendizado.setError("");
        }

        if(descricao.isEmpty()){
            layout_descricao_aprendizado.setError("Descrição obrigatória!");
        }else{
            layout_descricao_aprendizado.setError("");
        }

        if(relatoSelecionado.equals("Selecione...")){
            Toast.makeText(getContext(),"Selecione um relato para continuar.",Toast.LENGTH_SHORT).show();
        }

        if(!titulo.isEmpty() && !descricao.isEmpty() && !relatoSelecionado.equals("Selecione...")){
            progressDialog.setMessage("Cadastrando aprendizado...");
            progressDialog.show();

            Aprendizado aprendizado = new Aprendizado(maxid,relatoSelecionado,titulo,descricao,emailMentorado);

            FirebaseFirestore.getInstance().collection("mentorados").document(user.getUid()).collection("aprendizados")
                    .add(aprendizado)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro do aprendizado")
                                    .setMessage("Aprendizado cadastrado com sucesso!")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Aprendizado cadastrado com sucesso!",Toast.LENGTH_SHORT).show();

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_mentorado, new ListaAprendizadosFragment()).addToBackStack(null)
                                    .commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            builder.setTitle("Cadastro do aprendizado")
                                    .setMessage("Ops, houve um erro no cadastro do aprendizado! Tente novamente.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.create();
                            builder.show();
                            //Toast.makeText(getContext(),"Ops, houve um erro no cadastro do aprendizado! Tente novamente.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}