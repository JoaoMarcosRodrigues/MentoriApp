package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Adapters.ReuniaoAdapter;
import com.example.mentoriapp.Cadastro.CadastroReuniaoMentoradoFragment;
import com.example.mentoriapp.Classes.Reuniao;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaReunioesMentoradoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    View view;

    private ReuniaoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_reunioes_mentorado, container, false);

        FloatingActionButton addReuniao = view.findViewById(R.id.btnAdicionarReuniao);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentorados").document(user.getUid()).collection("reunioes");

        addReuniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentorado, new CadastroReuniaoMentoradoFragment()).addToBackStack(null)
                        .commit();
            }
        });

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        String email = user.getEmail();

        Query query = ref.whereEqualTo("emailAutor",email);
        FirestoreRecyclerOptions<Reuniao> options = new FirestoreRecyclerOptions.Builder<Reuniao>()
                .setQuery(query, Reuniao.class)
                .build();


        adapter = new ReuniaoAdapter(options);
        mRecyclerView = view.findViewById(R.id.listaReunioes);
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