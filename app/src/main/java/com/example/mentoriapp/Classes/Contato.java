package com.example.mentoriapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Contato implements Parcelable {
    
    private String uuid;
    private String username;
    private String lastMessage;
    private long timestamp;
    private String photoUrl;

    public Contato() {
    }

    public Contato(String uuid, String username, String lastMessage, long timestamp, String photoUrl) {
        this.uuid = uuid;
        this.username = username;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.photoUrl = photoUrl;
    }

    public Contato(Parcel in) {
        uuid = in.readString();
        username = in.readString();
        lastMessage = in.readString();
        timestamp = in.readLong();
        photoUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(username);
        dest.writeString(lastMessage);
        dest.writeLong(timestamp);
        dest.writeString(photoUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contato> CREATOR = new Creator<Contato>() {
        @Override
        public Contato createFromParcel(Parcel in) {
            return new Contato(in);
        }

        @Override
        public Contato[] newArray(int size) {
            return new Contato[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
