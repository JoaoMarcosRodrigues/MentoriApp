package com.example.mentoriapp.Listas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Detalhes.DetalheAprendizadoActivity;
import com.example.mentoriapp.Detalhes.DetalheRelatoActivity;
import com.example.mentoriapp.Detalhes.DetalhesRelatoMentoradoActivity;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.List;

public class ListaRelatosMentoradoActivity extends AppCompatActivity {

    private RecyclerView recycler_relatos;
    private Button btnFeedback;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GroupieAdapter adapter;
    private String emailMentorado;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_relatos_mentorado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        emailMentorado = bundle.getString("emailMentorado");
        String nomeMentorado = bundle.getString("nomeMentorado");
        getSupportActionBar().setTitle("Relatos de "+nomeMentorado);

        recycler_relatos = findViewById(R.id.recycler_relatos_mentorado);
        btnFeedback = findViewById(R.id.btnFeedback);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        adapter = new GroupieAdapter();
        recycler_relatos.setAdapter(adapter);
        recycler_relatos.setLayoutManager(new LinearLayoutManager(this));
        recycler_relatos.setHasFixedSize(true);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getApplicationContext(), DetalhesRelatoMentoradoActivity.class);
                RelatoItem relatoItem = (RelatoItem) item;
                intent.putExtra("relato",relatoItem.relato);

                startActivity(intent);
            }
        });

        fetchRelatos(emailMentorado);

        Toast.makeText(this,emailMentorado,Toast.LENGTH_SHORT).show();
    }

    private void fetchRelatos(String emailMentorado) {
        db.collection("relatos").whereEqualTo("emailMentorado",emailMentorado)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Relato relato = doc.toObject(Relato.class);
                            Log.d("Teste",relato.getTitulo());

                            adapter.add(new RelatoItem(relato));
                        }
                    }
                });
    }

    private class RelatoItem extends Item<GroupieViewHolder> {

        private final Relato relato;

        private RelatoItem(Relato relato){
            this.relato = relato;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloRelato = viewHolder.itemView.findViewById(R.id.txt_titulo_relato);
            TextView temaRelato = viewHolder.itemView.findViewById(R.id.txt_tema_relato);
            TextView dataRelato = viewHolder.itemView.findViewById(R.id.txt_data_relato);

            tituloRelato.setText(relato.getTitulo());
            temaRelato.setText(relato.getTema());
            dataRelato.setText(relato.getData());
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_relato;
        }
    }
}