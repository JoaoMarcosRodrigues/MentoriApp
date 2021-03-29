package com.example.mentoriapp.Fragmentos_side;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Mentorado.Mentorado;
import com.example.mentoriapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.GroupieViewHolder;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ChatFragment extends Fragment {

    private GroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setHasOptionsMenu(true);

        RecyclerView rv = view.findViewById(R.id.recycler_contatos);

        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchUsers();
        return view;
    }

    private void fetchUsers() {
        FirebaseFirestore.getInstance().collection("/mentorados")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.i("Teste", error.getMessage());
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc: docs){
                            Mentorado mentorado = doc.toObject(Mentorado.class);
                            Log.d("Teste",mentorado.getNome());
                            adapter.add(new UserItem(mentorado));

                            // PAROU AQUI! AULA 7
                        }
                    }
                });
    }

    private class UserItem extends Item<GroupieViewHolder>{

        private final Mentorado mentorado;

        public UserItem(Mentorado mentorado) {
            this.mentorado = mentorado;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txtNome = viewHolder.itemView.findViewById(R.id.txtNomeContato);
            ImageView imagePhoto = viewHolder.itemView.findViewById(R.id.imgContato);

            txtNome.setText(mentorado.getNome());

            Picasso.get()
                    .load(mentorado.getProfileUrl())
                    .into(imagePhoto);
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

}