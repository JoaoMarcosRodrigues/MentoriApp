package com.example.mentoriapp.Listas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.MentoresAdapter;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaMentoresFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    //private FirebaseUser user;
    View view;

    private MentoresAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lista_mentores, container, false);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("usuarios");
        auth = FirebaseAuth.getInstance();
        //user = auth.getCurrentUser();

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        //String email = user.getEmail();

        Query query = ref.orderBy("nome").whereEqualTo("tipo",1);
        FirestoreRecyclerOptions<Mentor> options = new FirestoreRecyclerOptions.Builder<Mentor>()
                .setQuery(query, Mentor.class)
                .build();


        adapter = new MentoresAdapter(options);
        mRecyclerView = view.findViewById(R.id.listaMentores);
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