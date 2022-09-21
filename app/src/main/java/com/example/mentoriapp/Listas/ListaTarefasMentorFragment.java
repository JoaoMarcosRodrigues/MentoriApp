package com.example.mentoriapp.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mentoriapp.Adapters.TarefaAdapter;
import com.example.mentoriapp.Cadastro.CadastroTarefaMentorFragment;
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.Detalhes.DetalheTarefaMentorActivity;
import com.example.mentoriapp.Detalhes.DetalheTarefaMentoradoActivity;
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

import java.util.List;

public class ListaTarefasMentorFragment extends Fragment {
    private RecyclerView recycler_tarefas;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GroupieAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_tarefas_mentor, container, false);

        FloatingActionButton addTarefa = view.findViewById(R.id.btnAdicionarTarefa);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("tarefas");

        recycler_tarefas = view.findViewById(R.id.listaTarefas);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        adapter = new GroupieAdapter();
        recycler_tarefas.setAdapter(adapter);
        recycler_tarefas.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_tarefas.setHasFixedSize(true);

        addTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentor, new CadastroTarefaMentorFragment()).addToBackStack(null)
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

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheTarefaMentorActivity.class);
                TarefaItem tarefaItem = (TarefaItem) item;
                intent.putExtra("tarefa",tarefaItem.tarefa);

                startActivity(intent);
            }
        });

        fetchTarefas();

        return view;
    }

    private void fetchTarefas() {
        db.collection("tarefas").whereEqualTo("emailAutor",user.getEmail()).orderBy("id")
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
                            Tarefa tarefa = doc.toObject(Tarefa.class);
                            Log.d("Teste",tarefa.getTitulo());

                            adapter.add(new TarefaItem(tarefa));
                        }
                    }
                });
    }

    private class TarefaItem extends Item<GroupieViewHolder> {

        private final Tarefa tarefa;

        private TarefaItem(Tarefa tarefa) {
            this.tarefa = tarefa;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloTarefa = viewHolder.itemView.findViewById(R.id.txt_titulo_tarefa);
            TextView descricaoTarefa = viewHolder.itemView.findViewById(R.id.txt_descricao_tarefa);

            tituloTarefa.setText(tarefa.getTitulo());
            descricaoTarefa.setText(tarefa.getDescricao());

        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_tarefa_mentor;
        }
    }
}