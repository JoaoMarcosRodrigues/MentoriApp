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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Listas.ListaFeedbacksFragment;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroFeedbackFragment extends Fragment {

    private TextInputEditText mEditDescricao,mEditTitulo;
    private Button mBtnCadastrar;
    private Spinner spinnerMentorado;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private CollectionReference ref;
    String mentoradoSelecionado;
    int maxid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_feedback, container, false);

        mEditTitulo = view.findViewById(R.id.edit_titulo_feedback);
        mEditDescricao = view.findViewById(R.id.edit_descricao_feedback);
        mBtnCadastrar = view.findViewById(R.id.btn_cadastrar_feedback);
        spinnerMentorado = view.findViewById(R.id.spinner_relatos);
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
        spinnerMentorado.setAdapter(arrayAdapter);

        ref.whereEqualTo("emailMentor",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("nomeMentorado");
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

        mBtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarFeedback();
            }
        });

        mFirestore.collection("mentores").document(user.getUid()).collection("feedbacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        return view;
    }

    private void cadastrarFeedback() {
        String descricao = mEditDescricao.getText().toString();
        String titulo = mEditTitulo.getText().toString();
        String emailMentor = user.getEmail();
        Date d = new Date();
        String dataFeedback = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(titulo.isEmpty() || titulo == null || descricao.isEmpty() || descricao == null){
            builder.setTitle("Campos obrigatórios")
                    .setMessage("Todos os campos são obrigatórios!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            builder.create();
            builder.show();
            //Toast.makeText(getContext(),"Descrição obrigatória!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Verificando dados...");
        progressDialog.show();

        Feedback feedback = new Feedback();
        feedback.setId(maxid);
        feedback.setTitulo(titulo);
        feedback.setData(dataFeedback);
        feedback.setEmailMentor(emailMentor);
        feedback.setDescricao(descricao);

        FirebaseFirestore.getInstance().collection("mentores").document(user.getUid()).collection("feedbacks")
                .add(feedback)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        builder.setTitle("Cadastro de feedback")
                                .setMessage("Feedback cadastrado com sucesso!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        builder.create();
                        builder.show();
                        //Toast.makeText(getContext(),"Feedback cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentor, new ListaFeedbacksFragment()).addToBackStack(null)
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        builder.setTitle("Cadastro de feedback")
                                .setMessage("Ops, houve um erro no cadastro do feedback. Tente novamente!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        builder.create();
                        builder.show();
                        //Toast.makeText(getContext(),"Ops, houve um erro no cadastro do feedback. Tente novamente!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}