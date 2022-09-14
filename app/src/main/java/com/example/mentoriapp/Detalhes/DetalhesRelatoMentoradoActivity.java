package com.example.mentoriapp.Detalhes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Listas.ListaFeedbacksFragment;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Date;

public class DetalhesRelatoMentoradoActivity extends AppCompatActivity {

    Relato relato;
    private Toolbar toolbar;
    private TextView txtTema,txtDescricao,txtPresencial,txtTarefa,txtData;
    private TextInputEditText editFeedback;
    private TextInputLayout inputFeedback;
    private Button btnFeedback;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    private FirebaseFirestore db;
    String tituloRelato;
    int idRelato;
    int maxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_relato_mentorado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTema = findViewById(R.id.txtTema);
        txtDescricao = findViewById(R.id.txtDescrição);
        txtPresencial = findViewById(R.id.txtPresencial);
        txtTarefa = findViewById(R.id.txtTarefa);
        txtData = findViewById(R.id.txtData);
        btnFeedback = findViewById(R.id.btnFeedback);
        editFeedback = findViewById(R.id.edit_descricao_feedback);
        inputFeedback = findViewById(R.id.layout_descricao_feedback);
        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        relato = bundle.getParcelable("relato");

        tituloRelato = relato.getTitulo();
        idRelato = relato.getId();
        getSupportActionBar().setTitle(tituloRelato);

        String tema = relato.getTema();
        String descricao = relato.getDescricao();
        String presencial = relato.getPresencial();
        String tarefaAssociada = relato.getTarefa_associada();
        String data = relato.getData();

        if(presencial.equals("Sim"))
            txtPresencial.setText("S");
        else
            txtPresencial.setText("N");

        txtTema.setText(tema);
        txtDescricao.setText(descricao);
        //txtPresencial.setText(presencial);
        txtTarefa.setText(tarefaAssociada);
        txtData.setText(data);

        db.collection("feedbacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                maxid = 1;
                for(DocumentSnapshot document : task.getResult()){
                    maxid++;
                }
            }
        });

        db.collection("feedbacks").whereEqualTo("id",idRelato).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String descricaoFeedback = document.getData().get("descricao").toString();
                                if(!descricaoFeedback.isEmpty() || descricaoFeedback != null){
                                    editFeedback.setText(document.getData().get("descricao").toString());
                                    Toast.makeText(getApplicationContext(),"Feedback já cadastrado!",Toast.LENGTH_SHORT).show();
                                    editFeedback.setEnabled(false);
                                    btnFeedback.setEnabled(false);
                                    return;
                                }
                            }
                        }
                    }
                });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = "Feedback "+maxid;
                String emailMentor = user.getEmail();
                String descricaoFeedback = editFeedback.getText().toString();
                Date d = new Date();
                String dataFeedback = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);

                if(descricaoFeedback == null || descricaoFeedback.isEmpty()){
                    inputFeedback.setError("Descrição do feedback obrigatória!");
                }else{
                    inputFeedback.setError("");
                }

                if(!descricaoFeedback.isEmpty()){
                    progressDialog.setMessage("Verificando dados...");
                    progressDialog.show();

                    Feedback feedback = new Feedback();
                    feedback.setId(maxid);
                    feedback.setTitulo(titulo);
                    feedback.setData(dataFeedback);
                    feedback.setEmailMentor(emailMentor);
                    feedback.setDescricao(descricaoFeedback);
                    feedback.setRelatoAssociado(tituloRelato);

                    db.collection("feedbacks")
                            .add(feedback)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Feedback cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                                    editFeedback.setEnabled(false);
                                    btnFeedback.setEnabled(false);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Ops, houve um erro no cadastro do feedback. Tente novamente!",Toast.LENGTH_SHORT).show();
                                }
                            });

                    // ATUALIZAR RELATO DO MENTORADO COM O FEEDBACK DO MENTOR
                    //db.collection("relatos").whereEqualTo("titulo",).
                }
            }
        });
    }
}