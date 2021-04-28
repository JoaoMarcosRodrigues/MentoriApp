package com.example.mentoriapp.Cadastro;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Listas.ListaFeedbacksFragment;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroFeedbackFragment extends Fragment {

    private EditText mEditDescricao;
    private Button mBtnCadastrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_feedback, container, false);

        mEditDescricao = view.findViewById(R.id.edit_descricao);
        mBtnCadastrar = view.findViewById(R.id.btn_cadastrar_feedback);
        progressDialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mBtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarFeedback();
            }
        });

        return view;
    }

    private void cadastrarFeedback() {
        String descricao = mEditDescricao.getText().toString();
        int id = 1;
        String emailMentor = user.getEmail();

        if(descricao.isEmpty() || descricao == null){
            Toast.makeText(getContext(),"Descrição obrigatória!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Verificando dados...");
        progressDialog.show();

        Feedback feedback = new Feedback();
        feedback.setId(id);
        feedback.setEmailMentor(emailMentor);
        feedback.setDescricao(descricao);

        FirebaseFirestore.getInstance().collection("feedbacks")
                .add(feedback)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Feedback cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_mentor, new ListaFeedbacksFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Ops, houve um erro no cadastro do feedback. Tente novamente!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}