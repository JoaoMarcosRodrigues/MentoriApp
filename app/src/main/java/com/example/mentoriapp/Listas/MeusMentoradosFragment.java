package com.example.mentoriapp.Listas;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploMeuMentoradoAdapter;
import com.example.mentoriapp.Adapters.ExemploRelatoAdapter;
import com.example.mentoriapp.Adapters.MeusMentoradosAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.Itens.ExemploItemMeuMentorado;
import com.example.mentoriapp.Itens.ExemploItemRelato;
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
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.ArrayList;
import java.util.List;

public class MeusMentoradosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    //private FirebaseUser user;
    View view;

    private GroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meus_mentorados, container, false);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentorados");
        auth = FirebaseAuth.getInstance();
        //user = auth.getCurrentUser();

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = ref.orderBy("nome");

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
        mRecyclerView = view.findViewById(R.id.recycler_meus_mentorados);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
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

            nome.setText(mentorado.getNome());
            areaAtuacao.setText(mentorado.getAreaAtuacao());

            Picasso.get()
                    .load(mentorado.getProfileUrl())
                    .into(imagePhoto);
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_meus_mentorados;
        }
    }
}