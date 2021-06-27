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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Adapters.TarefaAdapter;
import com.example.mentoriapp.Cadastro.CadastroFeedbackFragment;
import com.example.mentoriapp.Cadastro.CadastroTarefaMentoradoFragment;
import com.example.mentoriapp.Classes.Feedback;
import com.example.mentoriapp.Classes.Tarefa;
import com.example.mentoriapp.Detalhes.DetalheFeedbackActivity;
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

public class ListaTarefasMentoradoFragment extends Fragment {
    private RecyclerView recycler_tarefas;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GroupieAdapter adapter;
    View view;

    //private TarefaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_tarefas_mentorado, container, false);

        FloatingActionButton addTarefa = view.findViewById(R.id.btnAdicionarTarefa);
        Button salvarTarefa = view.findViewById(R.id.btnSalvarTarefa);


        recycler_tarefas = view.findViewById(R.id.listaTarefas);
        adapter = new GroupieAdapter();
        recycler_tarefas.setAdapter(adapter);
        recycler_tarefas.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_tarefas.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = db.collection("mentorados").document(user.getUid()).collection("tarefas");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(getContext(), DetalheTarefaMentoradoActivity.class);
                TarefaItem tarefaItem = (TarefaItem) item;
                intent.putExtra("tarefa",tarefaItem.tarefa);

                startActivity(intent);
            }
        });

        salvarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTarefa();
            }
        });

        addTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_mentorado, new CadastroTarefaMentoradoFragment()).addToBackStack(null)
                        .commit();
            }
        });

        fetchTarefas();

        return view;
    }

    private void fetchTarefas() {
        FirebaseFirestore.getInstance().collection("mentorados").document(user.getUid()).collection("tarefas")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

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

        private TarefaItem(Tarefa tarefa){
            this.tarefa = tarefa;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView tituloTarefa = viewHolder.itemView.findViewById(R.id.txt_titulo_tarefa);
            TextView descricaoTarefa = viewHolder.itemView.findViewById(R.id.txt_descricao_tarefa);
            Switch statusTarefa = viewHolder.itemView.findViewById(R.id.status_tarefa);

            tituloTarefa.setText(tarefa.getTitulo());
            descricaoTarefa.setText(tarefa.getDescricao());
            statusTarefa.setChecked(tarefa.isStatus());

            // MUDAR STATUS DA TAREFA AO MUDAR O SWITCH
            statusTarefa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        db.collection("mentorados").document(user.getUid()).collection("tarefas").document("5bBzkNvtWM0M1pTulwLa").update("status",true);
                    }else{
                        db.collection("mentorados").document(user.getUid()).collection("tarefas").document("5bBzkNvtWM0M1pTulwLa").update("status",false);
                    }
                }
            });
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_tarefa;
        }
    }

    private void salvarTarefa() {

    }
}