package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.DificuldadeAdapter;
import com.example.mentoriapp.Adapters.ExemploDificuldadeAdapter;
import com.example.mentoriapp.Cadastro.CadastroDificuldadeFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Detalhes.DetalheDificuldadeActivity;
import com.example.mentoriapp.Detalhes.DetalheRelatoActivity;
import com.example.mentoriapp.Itens.ExemploItemDificuldade;
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

import java.util.ArrayList;
import java.util.List;

public class ListaDificuldadesFragment extends Fragment {

    private RecyclerView recycler_dificuldades;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private Dificuldade dificuldade;
    private GroupieAdapter adapter;
    View view;

    //private DificuldadeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_dificuldades, container, false);

        recycler_dificuldades = view.findViewById(R.id.listaDificuldades);

        adapter = new GroupieAdapter();
        recycler_dificuldades.setAdapter(adapter);
        recycler_dificuldades.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_dificuldades.setHasFixedSize(true);

        FloatingActionButton addDificuldade = view.findViewById(R.id.btnAdicionarDificuldade);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentorados").document(user.getUid()).collection("dificuldades");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheDificuldadeActivity.class);
                DificuldadeItem relatoItem = (DificuldadeItem) item;
                intent.putExtra("dificuldade",relatoItem.dificuldade);

                startActivity(intent);
            }
        });

        addDificuldade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentorado, new CadastroDificuldadeFragment()).addToBackStack(null)
                        .commit();
            }
        });

        fetchDificuldades();

        return view;
    }

    private void fetchDificuldades() {
        FirebaseFirestore.getInstance().collection("mentorados").document(user.getUid()).collection("dificuldades")
                .orderBy("id")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Dificuldade dificuldade = doc.toObject(Dificuldade.class);
                            Log.d("Teste",dificuldade.getTagDificuldade());

                            adapter.add(new DificuldadeItem(dificuldade));
                        }
                    }
                });
    }

    private class DificuldadeItem extends Item<GroupieViewHolder> {

        private final Dificuldade dificuldade;

        private DificuldadeItem(Dificuldade dificuldade){
            this.dificuldade = dificuldade;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tagDificuldade = viewHolder.itemView.findViewById(R.id.txt_tag_dificuldade);
            TextView descricaoDificuldade = viewHolder.itemView.findViewById(R.id.txt_descricao_dificuldade);

            tagDificuldade.setText(dificuldade.getTagDificuldade());
            descricaoDificuldade.setText(dificuldade.getDescricaoDificuldade());
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_dificuldade;
        }
    }

}