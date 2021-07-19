package com.example.mentoriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentoriapp.network.ApiClient;
import com.example.mentoriapp.network.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        // REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse"
        // REMOTE_MSG_INVITATION_ACCEPTED = "accepted"
        // REMOTE_MSG_INVITATION_REJECTED = "rejected"

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra("meetingType");

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
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
        textUserEmail.setText(userEmail);

        ImageView imageAcceptInvitation = findViewById(R.id.imageAccept);
        imageAcceptInvitation.setOnClickListener(v -> sendInvitationResponse("accepted",getIntent().getStringExtra("inviterToken")));

        ImageView imageRejectInvitation = findViewById(R.id.imageReject);
        imageRejectInvitation.setOnClickListener(v -> sendInvitationResponse("rejected",getIntent().getStringExtra("inviterToken")));
    }

    public static HashMap<String,String> getRemoteMessageHeaders(){
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",
                "key=AAAAOF444NU:APA91bH9SXAXvEmaAk7XQKANJSBhmyu9VPXNLOHxvLOf8QAMdqXbpLmUb7zlXbhblKDHQOaL-Zngt-3jny66kJd9blqa_8WGxaEj1d1KhfxEwG5TOm6SrlyQQDM5YLPIsK_jrWZ2aOoO"
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
                        Toast.makeText(IncomingInvitationActivity.this,"Convite Aceito",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(IncomingInvitationActivity.this,"Convite Rejeitado",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(IncomingInvitationActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(IncomingInvitationActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}