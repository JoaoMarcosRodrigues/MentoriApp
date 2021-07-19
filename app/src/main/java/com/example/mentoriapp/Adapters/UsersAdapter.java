package com.example.mentoriapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentoriapp.Classes.Usuario;
import com.example.mentoriapp.R;
import com.example.mentoriapp.listeners.UsersListener;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<Usuario> usuarios;
    private UsersListener usersListener;

    public UsersAdapter(List<Usuario> usuarios, UsersListener usersListener) {
        this.usuarios = usuarios;
        this.usersListener = usersListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UserViewHolder holder, int position) {
        holder.setUserData(usuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView textFirstChar,textUserName,textUserEmail;
        ImageView imageAudioMeeting,imageVideoMeeting;

        UserViewHolder(View itemView) {
            super(itemView);
            textFirstChar = itemView.findViewById(R.id.textFirstChar);
            textUserName = itemView.findViewById(R.id.textUserName);
            textUserEmail = itemView.findViewById(R.id.textUserEmail);
            imageAudioMeeting = itemView.findViewById(R.id.imageAudioMeeting);
            imageVideoMeeting = itemView.findViewById(R.id.imageVideoMeeting);
        }

        void setUserData(Usuario user){
            textFirstChar.setText(user.getNome().substring(0,1));
            textUserName.setText(user.getNome());
            textUserEmail.setText(user.getEmail());
            imageAudioMeeting.setOnClickListener(view -> usersListener.initiateAudioMeeting(user));
            imageVideoMeeting.setOnClickListener(view -> usersListener.initiateVideoMeeting(user));
        }
    }
}
