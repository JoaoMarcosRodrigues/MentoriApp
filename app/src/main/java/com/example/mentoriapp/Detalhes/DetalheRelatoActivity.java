package com.example.mentoriapp.Detalhes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class DetalheRelatoActivity extends AppCompatActivity {

    Relato relato;
    private Toolbar toolbar;
    private TextView txtTema,txtDescricao,txtPresencial,txtTarefa,txtData,txtDescricaoFeedback;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_relato);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTema = findViewById(R.id.txtTema);
        txtDescricao = findViewById(R.id.txtDescrição);
        txtPresencial = findViewById(R.id.txtPresencial);
        txtTarefa = findViewById(R.id.txtTarefa);
        txtData = findViewById(R.id.txtData);
        txtDescricaoFeedback = findViewById(R.id.txtFeedback);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("feedbacks");

        Bundle bundle = getIntent().getExtras();
        relato = bundle.getParcelable("relato");

        String titulo = relato.getTitulo();
        getSupportActionBar().setTitle(titulo);

        String tema = relato.getTema();
        String descricao = relato.getDescricao();
        String presencial = relato.getPresencial();
        String tarefaAssociada = relato.getTarefa_associada();
        String data = relato.getData();

        if(presencial.equals("Sim"))
            txtPresencial.setText("S");
        else
            txtPresencial.setText("N");

        ref.whereEqualTo("relatoAssociado",titulo).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                txtDescricaoFeedback.setText(document.getData().get("descricao").toString());
                            }
                        } else {
                            Log.d("feedbacks", "Error: " + task.getException());
                        }
                    }
                });

        txtTema.setText(tema);
        txtDescricao.setText(descricao);
        //txtPresencial.setText(presencial);
        txtTarefa.setText(tarefaAssociada);
        txtData.setText(data);
        //txtDescricaoFeedback.setText(descricaoFeedback);

        //Toast.makeText(this,titulo+"-"+tema+"-"+data,Toast.LENGTH_SHORT).show();
    }
}