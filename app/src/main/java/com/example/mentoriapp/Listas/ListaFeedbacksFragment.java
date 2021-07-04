package com.example.mentoriapp.Listas;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.ExemploFeedbackAdapter;
import com.example.mentoriapp.Adapters.FeedbackAdapter;
import com.example.mentoriapp.Cadastro.CadastroAprendizadoFragment;
import com.example.mentoriapp.Cadastro.CadastroFeedbackFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Relato;
import com.example.mentoriapp.Detalhes.DetalheAprendizadoActivity;
import com.example.mentoriapp.Detalhes.DetalheFeedbackActivity;
import com.example.mentoriapp.Itens.ExemploItemFeedback;
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

public class ListaFeedbacksFragment extends Fragment {

    private RecyclerView recycler_feedbacks;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GroupieAdapter adapter;
    View view;

    //private FeedbackAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_feedbacks, container, false);

        recycler_feedbacks = view.findViewById(R.id.recycler_feedbacks);
        adapter = new GroupieAdapter();
        recycler_feedbacks.setAdapter(adapter);
        recycler_feedbacks.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_feedbacks.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("feedbacks");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheFeedbackActivity.class);
                FeedbackItem feedbackItemItem = (FeedbackItem) item;
                intent.putExtra("feedback",feedbackItemItem.feedback);

                startActivity(intent);
            }
        });

        fetchFeedbacks();

        return view;
    }

    private void fetchFeedbacks() {
        FirebaseFirestore.getInstance().collection("feedbacks").whereEqualTo("emailMentor",user.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Feedback feedback = doc.toObject(Feedback.class);
                            Log.d("Teste",feedback.getTitulo());

                            adapter.add(new FeedbackItem(feedback));
                        }
                    }
                });
    }

    private class FeedbackItem extends Item<GroupieViewHolder> {

        private final Feedback feedback;

        private FeedbackItem(Feedback feedback){
            this.feedback = feedback;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloRelato = viewHolder.itemView.findViewById(R.id.txt_titulo_feedback);
            TextView temaRelato = viewHolder.itemView.findViewById(R.id.txt_descricao_feedback);
            TextView dataRelato = viewHolder.itemView.findViewById(R.id.txt_data_feedback);

            tituloRelato.setText(feedback.getTitulo());
            temaRelato.setText(feedback.getDescricao());
            dataRelato.setText(feedback.getData());
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_feedback;
        }
    }
}