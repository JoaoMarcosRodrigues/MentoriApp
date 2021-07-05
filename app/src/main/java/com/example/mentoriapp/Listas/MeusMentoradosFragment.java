package com.example.mentoriapp.Listas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploMeuMentoradoAdapter;
import com.example.mentoriapp.Adapters.ExemploRelatoAdapter;
import com.example.mentoriapp.Adapters.MeusMentoradosAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.Classes.Mentoria;
import com.example.mentoriapp.Detalhes.DetalheAprendizadoActivity;
import com.example.mentoriapp.Detalhes.MentoradoPerfilVisitaActivity;
import com.example.mentoriapp.Itens.ExemploItemMeuMentorado;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MeusMentoradosFragment extends Fragment {

    private RecyclerView recycler_meus_mentorados;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    final String[] email = new String[1];
    ProgressDialog progressDialog;
    Button btnVerTodosMentorados;
    View view;
    int maxid;

    private GroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meus_mentorados, container, false);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentorados");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        progressDialog = new ProgressDialog(getContext());
        //user = auth.getCurrentUser();

        db.collection("mentorias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        verMeusMentorados();

        btnVerTodosMentorados = view.findViewById(R.id.btnVerTodosMentorados);

        btnVerTodosMentorados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verTodosMentorados();
            }
        });

        return view;
    }

    private void verMeusMentorados() {
        //Query query = ref.orderBy("nome");

        ref.whereEqualTo("emailMentor",user.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.i("Teste", error.getMessage());
                    return;
                }

                List<DocumentSnapshot> docs = value.getDocuments();
                for(DocumentSnapshot doc: docs){
                    Mentorado mentorado = doc.toObject(Mentorado.class);
                    //Log.d("Teste",mentor.getNome());
                    adapter.add(new UserItem(mentorado));
                }
            }
        });

        adapter = new GroupAdapter();
        recycler_meus_mentorados = view.findViewById(R.id.recycler_meus_mentorados);
        recycler_meus_mentorados.setHasFixedSize(true);
        recycler_meus_mentorados.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_meus_mentorados.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), MentoradoPerfilVisitaActivity.class);
                UserItem userItem = (UserItem) item;
                intent.putExtra("mentorado",userItem.mentorado);

                startActivity(intent);
            }
        });

    }

    private void verTodosMentorados() {
        //Query query = ref.orderBy("nome");

        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.i("Teste", error.getMessage());
                    return;
                }

                List<DocumentSnapshot> docs = value.getDocuments();
                for(DocumentSnapshot doc: docs){
                    Mentorado mentorado = doc.toObject(Mentorado.class);
                    //Log.d("Teste",mentor.getNome());
                    adapter.add(new UserItem(mentorado));
                }
            }
        });

        adapter = new GroupAdapter();
        recycler_meus_mentorados = view.findViewById(R.id.recycler_meus_mentorados);
        recycler_meus_mentorados.setHasFixedSize(true);
        recycler_meus_mentorados.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_meus_mentorados.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), MentoradoPerfilVisitaActivity.class);
                UserItem userItem = (UserItem) item;
                intent.putExtra("mentorado",userItem.mentorado);

                startActivity(intent);
            }
        });
    }

    private class UserItem extends Item<GroupieViewHolder> {

        private final Mentorado mentorado;

        public UserItem(Mentorado mentorado) {
            this.mentorado = mentorado;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            ImageView imagePhoto = viewHolder.itemView.findViewById(R.id.img_meu_mentorado);
            TextView nome = viewHolder.itemView.findViewById(R.id.txt_nome_mentorado);
            TextView areaAtuacao = viewHolder.itemView.findViewById(R.id.txt_area_atuacao_mentorado);
            Button btnIncluir = viewHolder.itemView.findViewById(R.id.btnIncluir);

            nome.setText(mentorado.getNome());
            areaAtuacao.setText(mentorado.getAreaAtuacao());

            Picasso.get()
                    .load(mentorado.getProfileUrl())
                    .into(imagePhoto);

            db.collection("mentorias").whereEqualTo("emailMentorado",mentorado.getEmail()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.exists()){
                                        //Toast.makeText(getApplicationContext(),"Feedback já cadastrado!",Toast.LENGTH_SHORT).show();
                                        btnIncluir.setEnabled(false);
                                        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                                        btnIncluir.getBackground().setColorFilter(colorFilter);
                                        btnIncluir.setTextColor(Color.GRAY);
                                        return;
                                    }
                                }
                            }
                        }
                    });

            btnIncluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailMentor = user.getEmail();

                    adapter.clear();

                    progressDialog.setMessage("Incluindo Mentorado e criando Mentoria...");
                    progressDialog.show();

                    Mentoria mentoria = new Mentoria(maxid,emailMentor,mentorado.getEmail());

                    FirebaseFirestore.getInstance().collection("mentorias")
                            .add(mentoria)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(),"Mentoria cadastrada com sucesso!",Toast.LENGTH_SHORT).show();

                                    btnIncluir.setEnabled(false);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(),"Ops, houve um erro no cadastro da mentoria! Tente novamente.",Toast.LENGTH_SHORT).show();
                                }
                            });

                    db.collection("mentorados").document(mentorado.getUuid()).update("emailMentor",emailMentor);

                    /*
                    ref.whereEqualTo("nome",nome.getText()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Log.i("Teste", error.getMessage());
                                return;
                            }

                            List<DocumentSnapshot> docs = value.getDocuments();
                            for(DocumentSnapshot doc: docs){
                                Mentorado mentorado = doc.toObject(Mentorado.class);
                                email[0] = mentorado.getEmail();

                            }
                        }
                    });
                     */
                }
            });
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_meus_mentorados;
        }
    }
}