package com.example.rammichaelismusicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver receiver;

    ArrayList<SongObject> songObjectArrayList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String currentPhotoPath = null;
    File file;
    int playingSongIndex;


    boolean insertFlag=false;
    String tempo;

    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;
    int switchNumber = 0;



    private Button buttonInsert;
    private ImageButton cameraButton,galleryButton;
    ImageView songPicture,picToBeUsed;

    boolean isPlaying = false;
    ImageView playBtn;
    boolean pictureFlag=false,galleryFlag=false;

    Bitmap bitmap;

    Uri uri;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        IntentFilter filter = new IntentFilter("com.example.rammichaelismusicplayer.updateSong");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };








        final int NOTIF_ID=1;

        playBtn = findViewById(R.id.play_music_btn);




        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
                int playing = sp.getInt("isPlaying", -1);
                System.out.println(" YTR "+playing);
                if(songObjectArrayList.size()==0)
                    Toast.makeText(MainActivity.this, "Please add at least 1 song to play", Toast.LENGTH_SHORT).show();
                else if(playing==1 && isMyServiceRunning(MusicPlayerService.class))
                {
                    Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
                    intent.putExtra("list", songObjectArrayList);
                    intent.putExtra("command", "pause");
                    startService(intent);
                }
                else if(playing==0 && isMyServiceRunning(MusicPlayerService.class))
                {
                    Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
                    intent.putExtra("list", songObjectArrayList);
                    intent.putExtra("command", "play");
                    startService(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
                    intent.putExtra("list", songObjectArrayList);
                    intent.putExtra("command", "new_instance");
                    startService(intent);
                }

                if (switchNumber == 0)
                {
                    playBtn.setImageDrawable(getDrawable(R.drawable.avd_play_to_pause));
                    Drawable drawable = playBtn.getDrawable();

                    if(drawable instanceof AnimatedVectorDrawableCompat)
                    {
                        avd = (AnimatedVectorDrawableCompat) drawable;
                        avd.start();

                    }
                    else if(drawable instanceof AnimatedVectorDrawable)
                    {
                        avd2 = (AnimatedVectorDrawable) drawable;
                        avd2.start();
                    }
                    switchNumber++;
                }else {
                    playBtn.setImageDrawable(getResources().getDrawable(R.drawable.avd_pause_to_play));
                    Drawable drawable = playBtn.getDrawable();

                    if(drawable instanceof AnimatedVectorDrawableCompat)
                    {
                        avd = (AnimatedVectorDrawableCompat) drawable;
                        avd.start();

                    }
                    else if(drawable instanceof AnimatedVectorDrawable)
                    {
                        avd2 = (AnimatedVectorDrawable) drawable;
                        avd2.start();
                    }
                    switchNumber--;
                }
                /*
                if (isPlaying)
                {
                    playBtn.setText("Play");
                    stopMusic();
                }
                else
                {
                    playBtn.setText("Stop");
                    playMusic();
                }
                isPlaying = !isPlaying;
                 */
            }
        });


/*
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String channelId = null;
        if(Build.VERSION.SDK_INT>=26)
        {
            channelId="some_channel_id";
            CharSequence channelName = "Some Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId,  channelName, importance);

            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
        builder.setSmallIcon(android.R.drawable.ic_media_play);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout);

        Intent playIntent = new Intent(this,delete_second_activity.class);
        playIntent.putExtra("notif_txt","play");
        PendingIntent playPendingIntent = PendingIntent.getActivity(this,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_btn,playPendingIntent);

        Intent nextIntent = new Intent(this,delete_second_activity.class);
        nextIntent.putExtra("notif_txt","next");
        PendingIntent nextPendingIntent = PendingIntent.getActivity(this,1,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_btn,nextPendingIntent);

        Intent previousIntent = new Intent(this,delete_second_activity.class);
        previousIntent.putExtra("notif_txt","previous");
        PendingIntent previousPendingIntent = PendingIntent.getActivity(this,2,previousIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.previous_btn,previousPendingIntent);

        Intent pauseIntent = new Intent(this,delete_second_activity.class);
        pauseIntent.putExtra("notif_txt","pause");
        PendingIntent pausePendingIntent = PendingIntent.getActivity(this,3,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pause_btn,pausePendingIntent);

        builder.setContent(remoteViews);

        manager.notify(NOTIF_ID,builder.build());
*/

        try{
            FileInputStream fis = openFileInput("songsArray");
            ObjectInputStream ois = new ObjectInputStream(fis);
            songObjectArrayList = (ArrayList<SongObject>)ois.readObject();
            ois.close();



        } catch (FileNotFoundException e) {
            createExampleList();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (songObjectArrayList==null) {
            createExampleList();
        }
        buildRecyclerView();

        buttonInsert=findViewById(R.id.button_insert);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureFlag=false;
                galleryFlag=false;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View dialogView = getLayoutInflater().inflate(R.layout.add_song_dialog,null);

                EditText songnameET = dialogView.findViewById(R.id.songnameET);
                EditText artistnameET = dialogView.findViewById(R.id.artistnameET);
                EditText songlinkET = dialogView.findViewById(R.id.songlinkET);

                picToBeUsed=dialogView.findViewById(R.id.pictureToBeAdded);

                cameraButton = dialogView.findViewById(R.id.cameraImageButton);
                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Ensure that there's a camera activity to handle the intent
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File

                            }
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.example.rammichaelismusicplayer.provider",photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, 1);

                                Glide.with(MainActivity.this).load(currentPhotoPath).into(picToBeUsed);
                            }
                        }

                    }
                });

                galleryButton=dialogView.findViewById(R.id.galleryImageButton);
                galleryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        photoPickerIntent.setType("image/*");

                        if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(photoPickerIntent, 2);
                        }
                    }


                });

                builder.setView(dialogView).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if(pictureFlag)
                            songObjectArrayList.add(songObjectArrayList.size(),new SongObject(currentPhotoPath,songnameET.getText().toString(),artistnameET.getText().toString(),songlinkET.getText().toString()));
                        else if(galleryFlag)
                            songObjectArrayList.add(songObjectArrayList.size(),new SongObject(uri.toString(),songnameET.getText().toString(),artistnameET.getText().toString(),songlinkET.getText().toString()));
                        else
                            songObjectArrayList.add(songObjectArrayList.size(),new SongObject(getURLForResource(R.drawable.ic_android),songnameET.getText().toString(),artistnameET.getText().toString(),songlinkET.getText().toString()));
                        mAdapter.notifyDataSetChanged();

                        try {

                            FileOutputStream fos = openFileOutput("songsArray",MODE_PRIVATE);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(songObjectArrayList);
                            oos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });





        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);

                intent.putExtra("image",songObjectArrayList.get(position).getImageString());
                intent.putExtra("name",songObjectArrayList.get(position).getSongName());
                intent.putExtra("artist",songObjectArrayList.get(position).getArtist());
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println(requestCode+"  popotem "+resultCode+"   XX "+currentPhotoPath);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            pictureFlag = true;
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            galleryFlag=true;
            uri = data.getData();
            tempo=uri.toString();

            Glide.with(buttonInsert.getRootView()).load(uri).into(picToBeUsed);
        }
    }





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createExampleList()
    {

        songObjectArrayList = new ArrayList<>();
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.onemore),"One More Cup of Coffee","Bob Dylan","https://syntax.org.il/xtra/bob.m4a"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.sara),"Sara","Bob Dylan","https://syntax.org.il/xtra/bob1.m4a"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.bobdylan),"The Man in me","Bob Dylan","https://syntax.org.il/xtra/bob2.mp3"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.boom),"Boom Boom Pow","The Black Eyed Peas","https://drive.google.com/uc?id=1xOjH6CR8ysVBNiORJ-VWIQRPXlxwUjIR&export=download"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.igotta),"I Gotta Feeling","The Black Eyed Peas","https://drive.google.com/uc?id=18yHsRzhbS2zZVtRkA6lztWP_RxDd5dvF&export=download"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.smack),"Smack That","Akon Ft. Eminem","https://drive.google.com/uc?id=1_3h-Tq_r16iuCpWt3sPS2q2V54b7Ngrg&export=download"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.gotten),"Gotten","Slash Ft. Adam Levine","https://drive.google.com/u/0/uc?id=1SFB8LGaoT9giyp0-y1uXRX6cCDoHF7sJ&export=download"));
        songObjectArrayList.add(new SongObject(getURLForResource(R.drawable.drive),"Drive","Incubus","https://drive.google.com/u/0/uc?id=1iHBs6Wpjpw5TRHlvQxAhRYgW5RaFIiUu&export=download"));

        try {

            FileOutputStream fos = openFileOutput("songsArray", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(songObjectArrayList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(songObjectArrayList,getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
/*
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }*/


    public String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END , ItemTouchHelper.RIGHT  ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {




            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(songObjectArrayList, fromPosition, toPosition);

            mAdapter.notifyItemMoved(fromPosition, toPosition);

            if(isMyServiceRunning(MusicPlayerService.class)) {
                Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
                intent.putExtra("list", songObjectArrayList);
                intent.putExtra("command", "refresh");
                startService(intent);
            }

            try {

                FileOutputStream fos = openFileOutput("songsArray", MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(songObjectArrayList);
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return false;
        }

        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

            int position = viewHolder.getAdapterPosition();

            SharedPreferences sp = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
            playingSongIndex = sp.getInt("currPlaying", -1);

            if(playingSongIndex == position) {
                Toast.makeText(MainActivity.this, "This song is currently playing!", Toast.LENGTH_SHORT).show();
                mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
            else
            {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delete_song_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        // Prevent dialog close on back press button
                        return keyCode == KeyEvent.KEYCODE_BACK;
                    }
                });
                Button positive_btn = dialog.findViewById(R.id.yes_delete_btn);
                positive_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        songObjectArrayList.remove(position);
                        mAdapter.notifyItemRemoved(position);

                        try {

                            FileOutputStream fos = openFileOutput("songsArray", MODE_PRIVATE);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(songObjectArrayList);
                            oos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });

                Button negative_btn=dialog.findViewById(R.id.cancel_delete_btn);
                negative_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        dialog.dismiss();
                    }

                });

                dialog.show();
                mAdapter.notifyDataSetChanged();
        }
    }};}




