package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingInvitationActivity extends AppCompatActivity {

    private String inviterToken = null;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String meetingRoom = null;
    private String meetingType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        meetingType = getIntent().getStringExtra("type");

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }else{
                imageMeetingType.setImageResource(R.drawable.ic_audio);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUserName = findViewById(R.id.textUsername);
        TextView textUserEmail = findViewById(R.id.textEmail);

        Bundle bundle = getIntent().getExtras();
        Usuario usuario = bundle.getParcelable("user");

        if(usuario != null){
            textFirstChar.setText(usuario.getNome().substring(0,1));
            textUserName.setText(usuario.getNome());
            textUserEmail.setText(usuario.getEmail());
        }

        ImageView imageStopInvitation = findViewById(R.id.imageStopInvitation);
        imageStopInvitation.setOnClickListener(v -> {
            if(usuario != null){
                cancelInvitation(usuario.getToken());
            }
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    inviterToken = task.getResult();
                    if(meetingType != null && user != null){
                        initiateMeeting(meetingType,usuario.getToken());
                    }
                }
            }
        });

        /*
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                inviterToken = task.getResult().getToken();
                if(meetingType != null && user != null){
                    initiateMeeting(meetingType,usuario.getToken());
                }
            }
        });
         */

    }

    public static HashMap<String,String> getRemoteMessageHeaders(){
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",
                "key=AAAAOF444NU:APA91bFvNpg39XrSnEw16zWvAskBFSGTMnILBGcSdIxTmlDFMpyQhkmXd8dbDqROk_iHpo8J-VrywW5ajcb46MLF_2IFE-j0f_rdLARGc8WS1mb9N4K7g9prwFhUxFwm1-mwpnhpqVQo"
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

            meetingRoom = user.getUid()+"_"+ UUID.randomUUID().toString().substring(0,5);

            data.put("meetingRoom",meetingRoom);

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
                    }else if(type.equals("invitationResponse")){
                        Toast.makeText(OutgoingInvitationActivity.this,"Invitation cancelled",Toast.LENGTH_SHORT).show();
                        finish();
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

    private void cancelInvitation(String receiverToken){
        try{
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("type","invitationResponse");
            data.put("invitationResponse","cancelled");

            body.put("data",data);
            body.put("registration_ids",tokens);

            sendRemoteMessage(body.toString(),"invitationResponse");

        }catch (Exception exception){
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("invitationResponse");
            if(type != null){
                if(type.equals("accepted")){
                    try{
                        URL serverURl = new URL("https://meet.jit.si");

                        JitsiMeetConferenceOptions.Builder builder = new JitsiMeetConferenceOptions.Builder();
                        builder.setServerURL(serverURl);
                        builder.setWelcomePageEnabled(false);
                        builder.setRoom(meetingRoom);
                        if(meetingType.equals("audio")){
                            builder.setVideoMuted(true);
                        }
                        JitsiMeetActivity.launch(OutgoingInvitationActivity.this,builder.build());
                        finish();
                    }catch (Exception exception){
                        Toast.makeText(OutgoingInvitationActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else if(type.equals("rejected")){
                    Toast.makeText(context,"Convite rejeitado",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                invitationResponseReceiver,
                new IntentFilter("invitationResponse")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                invitationResponseReceiver
        );
    }
}