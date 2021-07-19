package com.example.mentoriapp.listeners;

import com.example.mentoriapp.Classes.Usuario;

public interface UsersListener {

    void initiateVideoMeeting(Usuario usuario);
    void initiateAudioMeeting(Usuario usuario);
}
