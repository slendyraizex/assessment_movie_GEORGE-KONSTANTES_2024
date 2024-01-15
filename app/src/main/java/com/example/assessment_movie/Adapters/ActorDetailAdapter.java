package com.example.assessment_movie.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assessment_movie.DetailDomain.Cast;
import com.example.assessment_movie.R;

import java.util.List;

public class ActorDetailAdapter extends RecyclerView.Adapter<ActorDetailAdapter.ViewHolder> {

    List<String> images;
    List<Cast> actors;
    Context context;

    public ActorDetailAdapter(List<String> images, List<Cast> actors) {
        this.images = images;
        this.actors = actors;
    }

    @NonNull
    @Override
    public ActorDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_actors, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorDetailAdapter.ViewHolder holder, int position) {
        Cast actor = actors.get(position);
        String imageUrl = "https://image.tmdb.org/t/p/original/" + images.get(position);
        Log.i("Glide Image URL", imageUrl);  // This will log the URL
        Glide.with(context).load(imageUrl).into(holder.pic);
        holder.actorName.setText(actor.getName());
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView actorName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.itemImages);
            actorName = itemView.findViewById(R.id.actorName);
        }
    }
}
