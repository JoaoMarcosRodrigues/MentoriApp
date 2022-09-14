package com.example.mentoriapp.Detalhes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalheTarefaMentoradoActivity extends AppCompatActivity {

    Tarefa tarefa;
    private Toolbar toolbar;
    private TextView txtDescricao;
    private CheckBox switchStatus;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private int idTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_tarefa_mentorado);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("tarefas");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtDescricao = findViewById(R.id.txtDescrição);
        switchStatus = findViewById(R.id.switchStatus);

        Bundle bundle = getIntent().getExtras();
        tarefa = bundle.getParcelable("tarefa");

        idTarefa = tarefa.getId();

        String titulo = tarefa.getTitulo();
        getSupportActionBar().setTitle(titulo);
        String descricao = tarefa.getDescricao();
        boolean status = tarefa.isStatus();

        txtDescricao.setText(descricao);
        if(status == true){
            switchStatus.setChecked(true);
        }else{
            switchStatus.setChecked(false);
        }

        // MUDAR STATUS DA TAREFA AO MUDAR O SWITCH
        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.collection("tarefas").document(String.valueOf(idTarefa)).update("status",true);
                }else{
                    db.collection("tarefas").document(String.valueOf(idTarefa)).update("status",false);
                }
            }
        });

        //Toast.makeText(this,titulo+"-"+descricao+"-"+status,Toast.LENGTH_SHORT).show();
    }
}