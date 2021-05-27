package com.example.mentoriapp.Listas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.ExemploRelatoAdapter;
import com.example.mentoriapp.Adapters.RelatoAdapter;
import com.example.mentoriapp.Cadastro.CadastroRelatoFragment;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Fragmentos_side.MentorHomeFragment;
import com.example.mentoriapp.Itens.ExemploItemRelato;
import com.example.mentoriapp.MainMentorActivity;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ListaRelatosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    View view;

    private RelatoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_relatos, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentorados").document(user.getUid()).collection("relatos");

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

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        String email = user.getEmail();

        Query query = ref.orderBy("data",Query.Direction.ASCENDING)
                .whereEqualTo("emailMentorado",email);
        FirestoreRecyclerOptions<Relato> options = new FirestoreRecyclerOptions.Builder<Relato>()
                .setQuery(query, Relato.class)
                .build();

        adapter = new RelatoAdapter(options);
        mRecyclerView = view.findViewById(R.id.listaRelatos);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    
}