package com.example.rammichaelismusicplayer;
import android.app.Notification;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MusicPlayerService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{

    private MediaPlayer player=new MediaPlayer();
    RemoteViews remoteViews;
    NotificationCompat.Builder builder;
    NotificationManager manager;
    ArrayList<SongObject> songs;
    int currentPlaying=0;
    final int NOTIFICATION_ID=1;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
        player.reset();

        SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currPlaying", 0);
        editor.commit();


        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        String channelId = "channel_id",channelName="Music channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
        }
         builder = new NotificationCompat.Builder(this,channelId);

        remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout);


        remoteViews.setTextViewText(R.id.notification_title,"Testing");






        Intent playIntent = new Intent(this,MusicPlayerService.class);
        playIntent.putExtra("command","play");
        PendingIntent playPendingIntent = PendingIntent.getService(this,0,playIntent,PendingIntent.FLAG_MUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.play_btn,playPendingIntent);

        Intent pauseIntent = new Intent(this,MusicPlayerService.class);
        pauseIntent.putExtra("command","pause");
        PendingIntent pausePendingIntent = PendingIntent.getService(this,1,pauseIntent,PendingIntent.FLAG_MUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.pause_btn,pausePendingIntent);

        Intent nextIntent = new Intent(this,MusicPlayerService.class);
        nextIntent.putExtra("command","next");
        PendingIntent nextPendingIntent = PendingIntent.getService(this,2,nextIntent,PendingIntent.FLAG_MUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.next_btn,nextPendingIntent);

        Intent prevIntent = new Intent(this,MusicPlayerService.class);
        prevIntent.putExtra("command","prev");
        PendingIntent prevPendingIntent = PendingIntent.getService(this,3,prevIntent,PendingIntent.FLAG_MUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.previous_btn,prevPendingIntent);

        Intent closeIntent = new Intent(this,MusicPlayerService.class);
        closeIntent.putExtra("command","close");
        PendingIntent closePendingIntent = PendingIntent.getService(this,4,closeIntent,PendingIntent.FLAG_MUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.close_btn,closePendingIntent);

        builder.setCustomContentView(remoteViews);
        builder.setSmallIcon(android.R.drawable.ic_media_play);

        startForeground(1,builder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String command = intent.getStringExtra("command");

        SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        switch(command){
            case "new_instance":
                if(!player.isPlaying()) {
                    editor.putInt("isPlaying", 1);
                    editor.commit();
                    songs = (ArrayList<SongObject>)intent.getSerializableExtra("list");
                    updateUIsongName(songs.get(currentPlaying).getSongName(),songs.get(currentPlaying).getArtist());

                    try {
                        player.setDataSource(songs.get(currentPlaying).getLink());
                        player.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    player.pause();
                    editor.putInt("isPlaying", 0);
                    editor.commit();
                }
                refreshSongs();
                break;
            case "play":
                if (!player.isPlaying())
                    player.start();
                editor.putInt("isPlaying", 1);
                editor.commit();
                break;
            case "next":
                if (player.isPlaying())
                    player.stop();
                playSong(true);
                updateUIsongName(songs.get(currentPlaying).getSongName(),songs.get(currentPlaying).getArtist());
                break;
            case "prev":
                if (player.isPlaying())
                    player.stop();
                playSong(false);
                updateUIsongName(songs.get(currentPlaying).getSongName(),songs.get(currentPlaying).getArtist());
                break;
            case "pause":
                if (player.isPlaying())
                    player.pause();
                editor.putInt("isPlaying", 0);
                editor.commit();
                break;
            case "close":
                editor.putInt("isPlaying", -1);
                editor.commit();
                stopSelf();
                break;
            case "refresh":
                refreshSongs();
                break;



        }

/*
        String link = intent.getStringExtra("link");

        if(!player.isPlaying()) {
            try {
                player.setDataSource(link);
                player.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

 */
        return super.onStartCommand(intent, flags, startId);
    }

    private void refreshSongs() {

        String currentSongPlaying;
        currentSongPlaying = songs.get(currentPlaying).getSongName();

        try{
            FileInputStream fis = openFileInput("songsArray");
            ObjectInputStream ois = new ObjectInputStream(fis);
            songs = (ArrayList<SongObject>)ois.readObject();
            ois.close();
            for(int i=0; i<songs.size(); i++) {

                if (songs.get(i).getSongName().equals(currentSongPlaying)) {
                    currentPlaying=i;
                    break;
                }
            }


        } catch (FileNotFoundException e) {
            stopSelf();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (songs==null) {
            stopSelf();
        }

        SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currPlaying", currentPlaying);
        editor.commit();
    }

    private void playSong(boolean isNext){
        refreshSongs();
        if(isNext)
        {
            currentPlaying++;
            if (currentPlaying== songs.size())
                currentPlaying=0;
        }
        else
        {
            currentPlaying--;
            if(currentPlaying<0)
                currentPlaying=songs.size()-1;
        }
        player.reset();
        try {
            player.setDataSource(songs.get(currentPlaying).getLink());
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currPlaying", -1);
        editor.commit();

        if(player!=null)
        {
            if (player.isPlaying())
                player.stop();
            player.release();
        }

    }


    @Override
    public void onCompletion(MediaPlayer mp) {

        playSong(true);
        updateUIsongName(songs.get(currentPlaying).getSongName(),songs.get(currentPlaying).getArtist());

    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
        /*
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_media_play).setContentTitle("Playing music").setContentText("Playing THIS_SONG, enjoy");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("some_channel_id","some channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("some_channel_id");
        }

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("playing",true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        startForeground(1,builder.build());
         */
        Glide.with(this)
                .asBitmap()
                .load(songs.get(currentPlaying).getImageString())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
                        remoteViews.setImageViewBitmap(R.id.songImageNotification, resource);
                        builder.setCustomBigContentView(remoteViews);
                        Notification notification = builder.build();
                        manager.notify(NOTIFICATION_ID, notification);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }


    public void updateUIsongName(String songName, String artist)
    {
        remoteViews.setTextViewText(R.id.notification_title,artist+" - "+songName);
        manager.notify(1, builder.build());

        if(songs.size() ==0)
            currentPlaying=0;

        // Write currentPosition to shared preferences for mainactivity to know what song is currently playing
        // then we can stop user from removing a song that is playing
        SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currPlaying", currentPlaying);
        editor.commit();


    }
}
