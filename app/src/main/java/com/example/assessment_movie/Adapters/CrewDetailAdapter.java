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
import com.example.assessment_movie.DetailDomain.Crew;
import com.example.assessment_movie.R;

import java.util.List;

public class CrewDetailAdapter extends RecyclerView.Adapter<CrewDetailAdapter.ViewHolder> {

    List<String> images;
    List<Crew> crew;
    Context context;

    public CrewDetailAdapter(List<String> images, List<Crew> crew) {
        this.images = images;
        this.crew = crew;
    }

    @NonNull
    @Override
    public CrewDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_actors, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewDetailAdapter.ViewHolder holder, int position) {
        Crew crewMember = crew.get(position);
        String imageUrl = "https://image.tmdb.org/t/p/original/" + images.get(position);
        Log.i("Glide Image URL", imageUrl);  // This will log the URL for testing reasons
        Glide.with(context).load(imageUrl).into(holder.pic);
        holder.crewName.setText(crewMember.getName());
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView crewName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.itemImages);
            crewName = itemView.findViewById(R.id.actorName);
        }
    }
}
