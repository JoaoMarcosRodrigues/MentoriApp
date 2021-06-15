package com.example.mentoriapp.Listas;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Adapters.AprendizadoAdapter;
import com.example.mentoriapp.Adapters.MentoresAdapter;
import com.example.mentoriapp.Classes.Aprendizado;
import com.example.mentoriapp.Classes.Mentor;
import com.example.mentoriapp.Classes.Mentorado;
import com.example.mentoriapp.Fragmentos_side.ChatMentoradoFragment;
import com.example.mentoriapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.ArrayList;
import java.util.List;

public class ListaMentoresFragment extends Fragment {

    private ArrayList<Mentor> mListaMentores = new ArrayList<Mentor>();
    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth auth;
    //private FirebaseUser user;
    View view;

    private GroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lista_mentores, container, false);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("mentores");
        auth = FirebaseAuth.getInstance();
        //user = auth.getCurrentUser();

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        //Query query = ref.orderBy("nome").whereEqualTo("tipo",1);
        FirebaseFirestore.getInstance().collection("mentores")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.i("Teste", error.getMessage());
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc: docs){
                            Mentor mentor = doc.toObject(Mentor.class);
                            adapter.add(new UserItem(mentor));
                        }
                    }
                });


        adapter = new GroupAdapter();
        mRecyclerView = view.findViewById(R.id.listaMentores);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private class UserItem extends Item<GroupieViewHolder> {

        private final Mentor mentor;

        public UserItem(Mentor mentor) {
            this.mentor = mentor;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            ImageView imagePhoto = viewHolder.itemView.findViewById(R.id.img_foto_perfil);
            TextView nome = viewHolder.itemView.findViewById(R.id.txt_nome);
            TextView formacao = viewHolder.itemView.findViewById(R.id.txt_formacao);
            TextView curriculo = viewHolder.itemView.findViewById(R.id.txt_curriculo);
            Button btnVerPerfil = viewHolder.itemView.findViewById(R.id.btnVerPerfil);

            nome.setText(mentor.getNome());
            formacao.setText(mentor.getFormacao());
            curriculo.setText(mentor.getCurriculo());

            Picasso.get()
                    .load(mentor.getProfileUrl())
                    .into(imagePhoto);

            btnVerPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.whereEqualTo("nome",nome.getText()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Log.i("Teste", error.getMessage());
                                return;
                            }

                            List<DocumentSnapshot> docs = value.getDocuments();
                            for(DocumentSnapshot doc: docs){
                                Mentor mentor = doc.toObject(Mentor.class);
                                Toast.makeText(getContext(),"Email: "+mentor.getEmail(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }

        @Override
        public int getLayout() {
            return R.layout.exemplo_item_mentores;
        }
    }

}