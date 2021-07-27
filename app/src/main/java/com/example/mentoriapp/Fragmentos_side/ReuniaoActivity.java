package com.example.mentoriapp.Fragmentos_side;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mentoriapp.Adapters.UsersAdapter;
import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.OutgoingInvitationActivity;
import com.example.mentoriapp.R;
import com.example.mentoriapp.listeners.UsersListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReuniaoActivity extends AppCompatActivity implements UsersListener {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private List<Usuario> users;
    private UsersAdapter usersAdapter;
    private TextView textError;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int REQUEST_CODE_BATTERY_OPTIMIZATIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniao);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textError = findViewById(R.id.textError);

        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Objects.requireNonNull(getSupportActionBar()).setTitle(user.getEmail());

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                sendFCMTokenToDatabase(task.getResult());
            }
        });

        /*
        FirebaseInstallations.getInstance().getToken(true)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null){
                        sendFCMTokenToDatabase(task.getResult().getToken());
                    }
                });

         */

        RecyclerView usersRecyclerView = findViewById(R.id.usersRecyclerView);
        users = new ArrayList<>();
        usersAdapter = new UsersAdapter(users,this);
        usersRecyclerView.setAdapter(usersAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getUsers);

        getUsers();
        checkForBatteryOptimizations();
    }

    private void getUsers(){
        swipeRefreshLayout.setRefreshing(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    swipeRefreshLayout.setRefreshing(false);
                    String myUserId = user.getUid();
                    if(task.isSuccessful() && task.getResult() != null){
                        users.clear();
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            if(myUserId.equals(documentSnapshot.getId())){
                                continue;
                            }
                            Usuario usuario = new Usuario();
                            usuario.setNome(documentSnapshot.getString("nome"));
                            usuario.setEmail(documentSnapshot.getString("email"));
                            usuario.setToken(documentSnapshot.getString("token"));

                            users.add(usuario);
                        }
                        if(users.size() > 0){
                            usersAdapter.notifyDataSetChanged();
                        }else{
                            textError.setText(String.format("%s","Lista de usuários vazia!"));
                            textError.setVisibility(View.VISIBLE);
                        }
                    }else{
                        textError.setText(String.format("%s","Lista de usuários vazia!"));
                        textError.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void sendFCMTokenToDatabase(String token){
        DocumentReference documentReference =
                db.collection("usuarios").document(
                    user.getUid()
                );

        documentReference.update("token",token)
                .addOnFailureListener(e -> Toast.makeText(ReuniaoActivity.this,"Não foi possível enviar o token: "+e.getMessage(),Toast.LENGTH_LONG).show());
    }

    @Override
    public void initiateVideoMeeting(Usuario usuario) {
        if(usuario.getToken() == null || usuario.getToken().trim().isEmpty()){
            Toast.makeText(this,usuario.getNome()+" não está disponível para vídeo chamada.",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(), OutgoingInvitationActivity.class);
            intent.putExtra("user",usuario); // Usuário é Serializable
            intent.putExtra("type","video");
            startActivity(intent);
        }
    }

    @Override
    public void initiateAudioMeeting(Usuario usuario) {
        if(usuario.getToken() == null || usuario.getToken().trim().isEmpty()){
            Toast.makeText(this,usuario.getNome()+" não está disponível para áudio chamada.",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
            intent.putExtra("user",usuario);
            intent.putExtra("type","audio");
            startActivity(intent);
        }
    }

    private void checkForBatteryOptimizations(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            if(!powerManager.isIgnoringBatteryOptimizations(getPackageName())){
                AlertDialog.Builder builder = new AlertDialog.Builder(ReuniaoActivity.this);
                builder.setTitle("Alerta");
                builder.setMessage("Otimização de bateria está ativado. Pode interromper serviços de segundo plano");
                builder.setPositiveButton("Desativar", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    startActivityForResult(intent,REQUEST_CODE_BATTERY_OPTIMIZATIONS);
                });
                builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_BATTERY_OPTIMIZATIONS){
            checkForBatteryOptimizations();
        }
    }
}