package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentoriapp.Classes.Contato;
import com.example.mentoriapp.Classes.Message;
import com.example.mentoriapp.Classes.Notificacao;
import com.example.mentoriapp.Classes.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private GroupieAdapter adapter;
    private Usuario usuario;
    private EditText editChat;
    private Usuario me;
    private FirebaseUser userCorrente;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuario = getIntent().getExtras().getParcelable("user");
        getSupportActionBar().setTitle(usuario.getNome());

        auth = FirebaseAuth.getInstance();
        userCorrente = auth.getCurrentUser();

        RecyclerView rv = findViewById(R.id.recycler_chat);
        editChat = findViewById(R.id.edit_chat);
        Button btnChat = findViewById(R.id.btn_enviar);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        adapter = new GroupieAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("usuarios")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        me = documentSnapshot.toObject(Usuario.class);
                        fetchMessages();
                    }
                });

    }

    private void fetchMessages() {
        if(me != null){
            String fromId = me.getUuid();
            String toId = usuario.getUuid();

            FirebaseFirestore.getInstance().collection("conversations")
                    .document(fromId)
                    .collection(toId)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            List<DocumentChange> documentChanges = value.getDocumentChanges();

                            if(documentChanges != null){
                                for(DocumentChange doc : documentChanges){
                                    if(doc.getType() == DocumentChange.Type.ADDED){
                                        Message message = doc.getDocument().toObject(Message.class);
                                        adapter.add(new MessageItem(message));
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void sendMessage() {
        String text = editChat.getText().toString();
        editChat.setText(null);

        String fromId = FirebaseAuth.getInstance().getUid();
        String toId = usuario.getUuid();
        long timestamp = System.currentTimeMillis();

        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setTimestamp(timestamp);
        message.setText(text);

        if(!message.getText().isEmpty()){
            FirebaseFirestore.getInstance().collection("conversations")
                    .document(fromId)
                    .collection(toId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste",documentReference.getId());

                            Contato contato = new Contato();
                            contato.setUuid(toId);
                            contato.setUsername(usuario.getNome());
                            contato.setPhotoUrl(usuario.getPhotoUrl());
                            contato.setTimestamp(message.getTimestamp());
                            contato.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("last-messages")
                                    .document(fromId)
                                    .collection("contatos")
                                    .document(toId)
                                    .set(contato);

                            if(usuario.isOnline()){
                                Notificacao notificacao = new Notificacao();
                                notificacao.setFromId(message.getFromId());
                                notificacao.setToId(message.getToId());
                                notificacao.setTimestamp(message.getTimestamp());
                                notificacao.setText(message.getText());
                                notificacao.setFromName(me.getNome());

                                FirebaseFirestore.getInstance().collection("notificacoes")
                                        .document(usuario.getToken())
                                        .set(notificacao);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste",e.getMessage(),e);
                        }
                    });

            FirebaseFirestore.getInstance().collection("conversations")
                    .document(toId)
                    .collection(fromId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste",documentReference.getId());

                            Contato contato = new Contato();
                            contato.setUuid(toId);
                            contato.setUsername(usuario.getNome());
                            contato.setPhotoUrl(usuario.getPhotoUrl());
                            contato.setTimestamp(message.getTimestamp());
                            contato.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("last-messages")
                                    .document(toId)
                                    .collection("contatos")
                                    .document(fromId)
                                    .set(contato);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste",e.getMessage(),e);
                        }
                    });
        }
    }

    private class MessageItem extends Item<GroupieViewHolder> {
        private final Message message;

        private MessageItem(Message message) {
            this.message = message;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txtMsg = viewHolder.itemView.findViewById(R.id.txt_message);
            ImageView imgMsg = viewHolder.itemView.findViewById(R.id.img_message_user);

            txtMsg.setText(message.getText());
            Picasso.get()
                    .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                            ? me.getPhotoUrl()
                            : usuario.getPhotoUrl())
                    .into(imgMsg);
        }

        @Override
        public int getLayout() {
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid()) ? R.layout.item_from_message : R.layout.item_to_message;
        }
    }
}