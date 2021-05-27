package com.example.mentoriapp.Listas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Adapters.TarefaAdapter;
import com.example.mentoriapp.Cadastro.CadastroTarefaMentorFragment;
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaTarefasMentorFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    View view;

    private TarefaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_tarefas_mentor, container, false);

        FloatingActionButton addTarefa = view.findViewById(R.id.btnAdicionarTarefa);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("mentores").document(user.getUid()).collection("tarefas");

        addTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentor, new CadastroTarefaMentorFragment()).addToBackStack(null)
                        .commit();
            }
        });

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        String email = user.getEmail();

        Query query = ref.whereEqualTo("emailMentor",email);
        FirestoreRecyclerOptions<Tarefa> options = new FirestoreRecyclerOptions.Builder<Tarefa>()
                .setQuery(query, Tarefa.class)
                .build();


        adapter = new TarefaAdapter(options);
        mRecyclerView = view.findViewById(R.id.listaTarefas);
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