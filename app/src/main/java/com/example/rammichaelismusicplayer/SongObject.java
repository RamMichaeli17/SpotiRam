package com.example.rammichaelismusicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;

public class SongObject implements Serializable {
    private String songName;
    private String artist;
    private String link;
    private String photo;


    public SongObject(String imageResource, String SongName, String Artist, String songLink) {
        photo = imageResource;
        songName = SongName;
        artist = Artist;
        link = songLink;
    }


    public void changeText1(String text) {
        songName = text;
    }

    public String getSongName() {
        return songName;
    }

    public String getLink() {
        return link;
    }

    public void setSongName(String mText1) {
        this.songName = mText1;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageString() {
        return photo;
    }
/*
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
        out.defaultWriteObject();

    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        photo = BitmapFactory.decodeStream(in);
        in.defaultReadObject();

    }*/
}