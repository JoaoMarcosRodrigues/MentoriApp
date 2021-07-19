package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.network.ApiClient;
import com.example.mentoriapp.network.ApiService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingInvitationActivity extends AppCompatActivity {

    private String inviterToken = null;
    private FirebaseAuth auth;
    private FirebaseUser user;
    // Authorization
    // Content-Type

    // type
    // invitation
    // meetingType
    // inviterToken
    // data
    // registration_ids

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                inviterToken = task.getResult().getToken();
            }
        });

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra("type");

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUserName = findViewById(R.id.textUsername);
        TextView textUserEmail = findViewById(R.id.textEmail);

        Usuario user = getIntent().getParcelableExtra("user");
        if(user != null){
            textFirstChar.setText(user.getNome().substring(0,1));
            textUserName.setText(user.getNome());
            textUserEmail.setText(user.getEmail());
        }

        ImageView imageStopInvitation = findViewById(R.id.imageStopInvitation);
        imageStopInvitation.setOnClickListener(v -> onBackPressed());

        if(meetingType != null && user != null){
            initiateMeeting(meetingType,user.getToken());
        }
    }

    public static HashMap<String,String> getRemoteMessageHeaders(){
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",
                "key=AAAAOF444NU:APA91bH9SXAXvEmaAk7XQKANJSBhmyu9VPXNLOHxvLOf8QAMdqXbpLmUb7zlXbhblKDHQOaL-Zngt-3jny66kJd9blqa_8WGxaEj1d1KhfxEwG5TOm6SrlyQQDM5YLPIsK_jrWZ2aOoO"
        );
        headers.put("Content-Type","application/json");

        return headers;
    }

    private void initiateMeeting(String meetingType, String receiverToken){
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("type","invitation");
            data.put("meetingType",meetingType);
            data.put("nome",user.getDisplayName());
            data.put("email",user.getEmail());
            data.put("inviterToken",inviterToken);

            body.put("data",data);
            body.put("registration_ids",tokens);

            sendRemoteMessage(body.toString(),"invitation");

        }catch (Exception exception){
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void sendRemoteMessage(String remoteMessageBody, String type){
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                getRemoteMessageHeaders(),
                remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                if(response.isSuccessful()){
                    if(type.equals("invitation")){
                        Toast.makeText(OutgoingInvitationActivity.this,"Convite enviado com sucesso",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(OutgoingInvitationActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(OutgoingInvitationActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}