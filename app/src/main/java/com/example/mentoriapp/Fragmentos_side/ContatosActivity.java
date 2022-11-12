package com.example.mentoriapp.Fragmentos_side;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentoriapp.ChatActivity;
import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.List;

public class ContatosActivity extends AppCompatActivity {

    private GroupieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contatos);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rv = findViewById(R.id.recycler_contatos);
        adapter = new GroupieAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(ContatosActivity.this, ChatActivity.class);
                UserItem userItem = (UserItem) item;
                intent.putExtra("user",userItem.usuario);

                startActivity(intent);
            }
        });

        fetchUsers();
    }

    private void fetchUsers() {
        FirebaseFirestore.getInstance().collection("usuarios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(),error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        adapter.clear();
                        for(DocumentSnapshot doc : docs){
                            Usuario usuario = doc.toObject(Usuario.class);
                            String uid = FirebaseAuth.getInstance().getUid();
                            if(usuario.getUuid().equals(uid))
                                continue;
                            Log.d("Teste",usuario.getNome());

                            adapter.add(new UserItem(usuario));
                        }
                    }
                });
    }

    private class UserItem extends Item<GroupieViewHolder> {

        private final Usuario usuario;

        private UserItem(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txtUserName = viewHolder.itemView.findViewById(R.id.txtNomeUser);
            ImageView imgUser = viewHolder.itemView.findViewById(R.id.imgPhotoUser);

            txtUserName.setText(usuario.getNome());

            Picasso.get()
                    .load(usuario.getPhotoUrl())
                    .into(imgUser);
        }

        @Override
        public int getLayout() {
            return R.layout.item_usuario;
        }
    }
}