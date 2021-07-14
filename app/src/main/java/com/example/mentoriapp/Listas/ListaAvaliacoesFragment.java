package com.example.mentoriapp.Listas;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.AvaliacaoAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Cadastro.CadastroAvaliacaoFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Avaliacao;
import com.example.mentoriapp.Detalhes.DetalheAprendizadoActivity;
import com.example.mentoriapp.Detalhes.DetalheAvaliacaoActivity;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.List;

public class ListaAvaliacoesFragment extends Fragment {

    private RecyclerView recycler_avaliacoes;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GroupieAdapter adapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lista_avaliacoes, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("mentores").document(user.getUid()).collection("avaliacoes");


        recycler_avaliacoes = view.findViewById(R.id.listaAvaliacoes);
        adapter = new GroupieAdapter();
        recycler_avaliacoes.setAdapter(adapter);
        recycler_avaliacoes.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_avaliacoes.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentores").document(user.getUid()).collection("avaliacoes");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheAvaliacaoActivity.class);
                AvaliacaoItem avaliacaoItem = (AvaliacaoItem) item;
                intent.putExtra("avaliacao",avaliacaoItem.avaliacao);

                startActivity(intent);
            }
        });


        fetchAvaliacao();

        return view;
    }

    private void fetchAvaliacao() {
        FirebaseFirestore.getInstance().collection("mentores").document(user.getUid()).collection("avaliacoes").orderBy("id")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

                        adapter.clear();
                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Avaliacao avaliacao = doc.toObject(Avaliacao.class);
                            Log.d("Teste",avaliacao.getTituloAvaliacao());

                            adapter.add(new AvaliacaoItem(avaliacao));
                        }
                    }
                });
    }

    private class AvaliacaoItem extends Item<GroupieViewHolder> {

        private final Avaliacao avaliacao;

        private AvaliacaoItem(Avaliacao avaliacao){
            this.avaliacao = avaliacao;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloAvaliacao = viewHolder.itemView.findViewById(R.id.txt_titulo_avaliacao);
            TextView descricaoAvaliacao = viewHolder.itemView.findViewById(R.id.txt_descricao_avaliacao);

            tituloAvaliacao.setText(avaliacao.getTituloAvaliacao());
            descricaoAvaliacao.setText(avaliacao.getDescricaoAvaliacao());
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_avaliacao;
        }
    }
}