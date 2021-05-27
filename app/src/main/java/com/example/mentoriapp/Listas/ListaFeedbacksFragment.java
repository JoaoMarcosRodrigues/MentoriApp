package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploFeedbackAdapter;
import com.example.mentoriapp.Adapters.FeedbackAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Cadastro.CadastroFeedbackFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Itens.ExemploItemFeedback;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ListaFeedbacksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    View view;

    private FeedbackAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_feedbacks, container, false);

        FloatingActionButton addFeedback = view.findViewById(R.id.btnAdicionarFeedback);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("mentores").document(user.getUid()).collection("feedbacks");

        addFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentor, new CadastroFeedbackFragment()).addToBackStack(null)
                        .commit();
            }
        });

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        String email = user.getEmail();

        Query query = ref.whereEqualTo("emailMentor",email);
        FirestoreRecyclerOptions<Feedback> options = new FirestoreRecyclerOptions.Builder<Feedback>()
                .setQuery(query, Feedback.class)
                .build();


        adapter = new FeedbackAdapter(options);
        mRecyclerView = view.findViewById(R.id.recycler_feedbacks);
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