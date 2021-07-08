package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploAprendizadoAdapter;
import com.example.mentoriapp.Adapters.RelatoAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Detalhes.DetalheAprendizadoActivity;
import com.example.mentoriapp.Detalhes.DetalheRelatoActivity;
import com.example.mentoriapp.Itens.ExemploItemAprendizado;
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
import java.util.Collection;
import java.util.List;

public class ListaAprendizadosFragment extends Fragment {

    private RecyclerView recycler_aprendizados;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Aprendizado aprendizado;
    private GroupieAdapter adapter;
    View view;

    //private AprendizadoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_aprendizados, container, false);

        FloatingActionButton addAprendizado = view.findViewById(R.id.btnAdicionarAprendizado);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        recycler_aprendizados = view.findViewById(R.id.listaAprendizados);
        adapter = new GroupieAdapter();
        recycler_aprendizados.setAdapter(adapter);
        recycler_aprendizados.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_aprendizados.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentorados").document(user.getUid()).collection("aprendizados");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheAprendizadoActivity.class);
                AprendizadoItem aprendizadoItem = (AprendizadoItem) item;
                intent.putExtra("aprendizado",aprendizadoItem.aprendizado);

                startActivity(intent);
            }
        });

        addAprendizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentorado, new CadastroAprendizadoFragment()).addToBackStack(null)
                        .commit();
            }
        });

        fetchAprendizados();


        return view;
    }

    private void fetchAprendizados() {
        FirebaseFirestore.getInstance().collection("mentorados").document(user.getUid()).collection("aprendizados").orderBy("id")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Aprendizado aprendizado = doc.toObject(Aprendizado.class);
                            Log.d("Teste",aprendizado.getTituloAprendizado());

                            adapter.add(new AprendizadoItem(aprendizado));
                        }
                    }
                });
    }

    private class AprendizadoItem extends Item<GroupieViewHolder>{

        private final Aprendizado aprendizado;

        private AprendizadoItem(Aprendizado aprendizado){
            this.aprendizado = aprendizado;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloAprendizado = viewHolder.itemView.findViewById(R.id.txt_titulo_aprendizado);
            TextView descricaoAprendizado = viewHolder.itemView.findViewById(R.id.txt_descricao_aprendizado);

            tituloAprendizado.setText(aprendizado.getTituloAprendizado());
            descricaoAprendizado.setText(aprendizado.getDescricaoAprendizado());
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_aprendizado;
        }

    }
}