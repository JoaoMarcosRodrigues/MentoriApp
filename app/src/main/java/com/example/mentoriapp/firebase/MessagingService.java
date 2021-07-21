package com.example.mentoriapp.firebase;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mentoriapp.IncomingInvitationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type = remoteMessage.getData().get("type");

        if(type != null){
            if(type.equals("invitation")){
                Intent intent = new Intent(getApplicationContext(), IncomingInvitationActivity.class);
                intent.putExtra("meetingType",remoteMessage.getData().get("meetingType"));
                intent.putExtra("nome",remoteMessage.getData().get("nome"));
                intent.putExtra("email",remoteMessage.getData().get("email"));
                intent.putExtra("inviterToken",remoteMessage.getData().get("inviterToken"));
                intent.putExtra("meetingRoom",remoteMessage.getData().get("meetingRoom"));

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if(type.equals("invitationResponse")){
                Intent intent = new Intent("invitationResponse");
                intent.putExtra("invitationResponse",remoteMessage.getData().get("invitationResponse"));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        }
    }
}
