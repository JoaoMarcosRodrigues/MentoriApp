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

import com.example.mentoriapp.network.ApiClient;
import com.example.mentoriapp.network.ApiService;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingInvitationActivity extends AppCompatActivity {

    private String meetingType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        // REMOTE_MSG_MEETING_ROOM = "meetingRoom";

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        meetingType = getIntent().getStringExtra("meetingType");

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

        String userName = getIntent().getStringExtra("nome");
        String userEmail = getIntent().getStringExtra("email");
        if(userName != null){
            textFirstChar.setText(userName.substring(0,1));
        }

        textUserName.setText(userName);
        textUserEmail.setText(userEmail+"AQUI!!");

        ImageView imageAcceptInvitation = findViewById(R.id.imageAccept);
        imageAcceptInvitation.setOnClickListener(v -> sendInvitationResponse("accepted",getIntent().getStringExtra("inviterToken")));

        ImageView imageRejectInvitation = findViewById(R.id.imageReject);
        imageRejectInvitation.setOnClickListener(v -> sendInvitationResponse("rejected",getIntent().getStringExtra("inviterToken")));
    }

    public static HashMap<String,String> getRemoteMessageHeaders(){
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",
                "key=AAAAOF444NU:APA91bFvNpg39XrSnEw16zWvAskBFSGTMnILBGcSdIxTmlDFMpyQhkmXd8dbDqROk_iHpo8J-VrywW5ajcb46MLF_2IFE-j0f_rdLARGc8WS1mb9N4K7g9prwFhUxFwm1-mwpnhpqVQo"
        );
        headers.put("Content-Type","application/json");

        return headers;
    }

    private void sendInvitationResponse(String type, String receiverToken){
        try{
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("type","invitationResponse");
            data.put("invitationResponse",type);

            body.put("data",data);
            body.put("registration_ids",tokens);

            sendRemoteMessage(body.toString(),type);

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
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()){
                    if(type.equals("accepted")){
                        try{
                            URL serverURL = new URL("https://meet.jit.si");
                            JitsiMeetConferenceOptions.Builder builder = new JitsiMeetConferenceOptions.Builder();
                            builder.setServerURL(serverURL);
                            builder.setWelcomePageEnabled(false);
                            builder.setRoom(getIntent().getStringExtra("meetingRoom"));
                            if(meetingType.equals("audio")){
                                builder.setVideoMuted(true);
                            }
                            JitsiMeetActivity.launch(IncomingInvitationActivity.this,builder.build());
                            finish();
                        }catch (Exception exception){
                            Toast.makeText(IncomingInvitationActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }else{
                        Toast.makeText(IncomingInvitationActivity.this,"Convite Rejeitado",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Toast.makeText(IncomingInvitationActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(IncomingInvitationActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("invitationResponse");
            if(type != null){
                if(type.equals("cancelled")){
                    Toast.makeText(context,"Convite cancelado",Toast.LENGTH_SHORT).show();
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