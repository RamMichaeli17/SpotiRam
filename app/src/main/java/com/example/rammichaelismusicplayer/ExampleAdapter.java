package com.example.rammichaelismusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<SongObject> songObjectArrayList;
    private OnItemClickListener mListener;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView songImageIV;
        public TextView songNameTV;
        public TextView songArtistTV;

        public ExampleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            songImageIV =itemView.findViewById(R.id.songPictureIV);
            songNameTV =itemView.findViewById(R.id.textViewName);
            songArtistTV =itemView.findViewById(R.id.textViewArt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION);
                        listener.onItemClick(position);
                    }
                }
            });

        }
    }

    public ExampleAdapter(ArrayList<SongObject> songslist, Context context)
    {
        songObjectArrayList = songslist;
        this.context = context;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        SongObject currentItem = songObjectArrayList.get(position);

        Glide.with(context).load(currentItem.getImageString()).into(holder.songImageIV);

        holder.songNameTV.setText(currentItem.getSongName());
        holder.songArtistTV.setText(currentItem.getArtist());
    }

    @Override
    public int getItemCount() {
        return songObjectArrayList.size();
    }


}
