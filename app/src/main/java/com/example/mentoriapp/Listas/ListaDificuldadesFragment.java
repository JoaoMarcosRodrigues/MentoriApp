package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.DificuldadeAdapter;
import com.example.mentoriapp.Adapters.ExemploDificuldadeAdapter;
import com.example.mentoriapp.Cadastro.CadastroDificuldadeFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Dificuldade;
import com.example.mentoriapp.Itens.ExemploItemDificuldade;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ListaDificuldadesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    View view;

    private DificuldadeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_dificuldades, container, false);

        FloatingActionButton addDificuldade = view.findViewById(R.id.btnAdicionarDificuldade);
        mRecyclerView = view.findViewById(R.id.listaDificuldades);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("dificuldades");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        addDificuldade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentorado, new CadastroDificuldadeFragment()).addToBackStack(null)
                        .commit();
            }
        });

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        String email = user.getEmail();

        Query query = ref.whereEqualTo("emailMentorado",email);
        FirestoreRecyclerOptions<Dificuldade> options = new FirestoreRecyclerOptions.Builder<Dificuldade>()
                .setQuery(query, Dificuldade.class)
                .build();


        adapter = new DificuldadeAdapter(options);
        mRecyclerView = view.findViewById(R.id.listaDificuldades);
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