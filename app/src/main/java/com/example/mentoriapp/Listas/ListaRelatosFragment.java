package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Cadastro.CadastroRelatoFragment;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Detalhes.DetalheRelatoActivity;
import com.example.mentoriapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class ListaRelatosFragment extends Fragment {

    private RecyclerView recycler_relatos;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GroupieAdapter adapter;
    View view;

    //private RelatoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_relatos, container, false);

        recycler_relatos = view.findViewById(R.id.listaRelatos);

        adapter = new GroupieAdapter();
        recycler_relatos.setAdapter(adapter);
        recycler_relatos.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_relatos.setHasFixedSize(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ref = db.collection("relatos");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheRelatoActivity.class);
                RelatoItem relatoItem = (RelatoItem) item;
                intent.putExtra("relato",relatoItem.relato);

                startActivity(intent);
            }
        });

        FloatingActionButton addRelato = view.findViewById(R.id.btnAdicionarRelato);

        addRelato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentorado, new CadastroRelatoFragment()).addToBackStack(null)
                        .commit();
            }
        });

        fetchRelatos();

        return view;
    }

    private void fetchRelatos() {
        FirebaseFirestore.getInstance().collection("relatos").whereEqualTo("emailMentorado",user.getEmail()).orderBy("data")
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