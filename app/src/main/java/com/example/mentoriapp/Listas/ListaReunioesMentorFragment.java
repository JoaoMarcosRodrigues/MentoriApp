package com.example.mentoriapp.Listas;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mentoriapp.Adapters.ReuniaoAdapter;
import com.example.mentoriapp.Adapters.ReuniaoMentorAdapter;
import com.example.mentoriapp.Cadastro.CadastroReuniaoMentorFragment;
import com.example.mentoriapp.Cadastro.CadastroReuniaoMentoradoFragment;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Reuniao;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class ListaReunioesMentorFragment extends Fragment {

    private RecyclerView recycler_reunioes;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private GroupieAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;

    //private ReuniaoMentorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_reunioes_mentor, container, false);

        FloatingActionButton addReuniao = view.findViewById(R.id.btnAdicionarReuniao);

        recycler_reunioes = view.findViewById(R.id.listaReunioes);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        adapter = new GroupieAdapter();
        recycler_reunioes.setAdapter(adapter);
        recycler_reunioes.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_reunioes.setHasFixedSize(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ref = db.collection("reunioes");

        addReuniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentor, new CadastroReuniaoMentorFragment()).addToBackStack(null)
                        .commit();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        fetchReunioes();

        return view;
    }

    private void fetchReunioes() {
        FirebaseFirestore.getInstance().collection("reunioes").whereEqualTo("emailAutor",user.getEmail()).orderBy("data", Query.Direction.DESCENDING)
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
                            Reuniao reuniao = doc.toObject(Reuniao.class);
                            Log.d("Teste",reuniao.getTitulo());

                            adapter.add(new ReuniaoItem(reuniao));
                        }
                    }
                });
    }

    private class ReuniaoItem extends Item<GroupieViewHolder> {

        private final Reuniao reuniao;

        private ReuniaoItem(Reuniao reuniao){
            this.reuniao = reuniao;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloReuniao = viewHolder.itemView.findViewById(R.id.txt_titulo_reuniao);
            TextView descricaoReuniao = viewHolder.itemView.findViewById(R.id.txt_descricao_reuniao);
            TextView dataReuniao = viewHolder.itemView.findViewById(R.id.txt_data_reuniao);
            TextView horarioReuniao = viewHolder.itemView.findViewById(R.id.txt_horario_reuniao);

            tituloReuniao.setText(reuniao.getTitulo());
            descricaoReuniao.setText(reuniao.getDescricao());
            dataReuniao.setText(reuniao.getData());
            horarioReuniao.setText(reuniao.getHorario());
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_reuniao;
        }

    }
}