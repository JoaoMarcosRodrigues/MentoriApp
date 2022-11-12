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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentoriapp.ChatActivity;
import com.example.mentoriapp.Classes.ChatApplication;
import com.example.mentoriapp.Classes.Contato;
import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private GroupieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_messages);

        ChatApplication application = (ChatApplication) getApplication();

        getApplication().registerActivityLifecycleCallbacks(application);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.recycler_messages);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupieAdapter();
        rv.setAdapter(adapter);

        /*
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(MessagesActivity.this, ChatActivity.class);
                ContatoItem contatoItem = (ContatoItem) item;
                intent.putExtra("contato",contatoItem.contato);

                startActivity(intent);
            }
        });
         */

        updateToken();

        fetchLastMessages();
    }

    private void updateToken() {
        String uid = FirebaseAuth.getInstance().getUid();

        FirebaseInstallations.getInstance().getToken(true)
                .addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
                    @Override
                    public void onComplete(Task<InstallationTokenResult> task) {
                        String token = task.getResult().getToken();

                        if (task.isSuccessful() && task.getResult() != null && uid != null) {
                            Log.d("Installations", "Installation auth token: " + task.getResult().getToken());
                            FirebaseFirestore.getInstance().collection("usuarios")
                                    .document(uid)
                                    .update("token",token);
                        } else {
                            Log.e("Installations", "Unable to get Installation auth token");
                        }
                    }
                });


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