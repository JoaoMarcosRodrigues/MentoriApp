package com.example.mentoriapp.Fragmentos_side;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentoriapp.Classes.Contato;
import com.example.mentoriapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private GroupieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_messages);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.recycler_contato);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupieAdapter();

        rv.setAdapter(adapter);

        fetchLastMessages();

    }

    private void fetchLastMessages() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("last-messages")
                .document(uid)
                .collection("contatos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<DocumentChange> documentChangeList = value.getDocumentChanges();

                        if(documentChangeList != null){
                            for(DocumentChange doc : documentChangeList){
                                if(doc.getType() == DocumentChange.Type.ADDED){
                                    Contato contato = doc.getDocument().toObject(Contato.class);

                                    adapter.add(new ContatoItem(contato));
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mensagens,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.contatos){
            Intent intent = new Intent(MessagesActivity.this,ContatosActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private class ContatoItem extends Item<GroupieViewHolder>{

        private final Contato contato;

        private ContatoItem(Contato contato) {
            this.contato = contato;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txtNomeUsuario = viewHolder.itemView.findViewById(R.id.txtNomeUser);
            TextView txtLastMessage = viewHolder.itemView.findViewById(R.id.txtLastMessage);
            ImageView imgUsuarioMessage = viewHolder.itemView.findViewById(R.id.imgPhotoUser);

            txtNomeUsuario.setText(contato.getUsername());
            txtLastMessage.setText(contato.getLastMessage());

            Picasso.get()
                    .load(contato.getPhotoUrl())
                    .into(imgUsuarioMessage);

        }

        @Override
        public int getLayout() {
            return R.layout.item_usuario_message;
        }
    }
}