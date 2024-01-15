package com.example.assessment_movie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.assessment_movie.DetailActivity;
import com.example.assessment_movie.Domain.MovieList;
import com.example.assessment_movie.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    MovieList items;
    Context context;

    public MovieAdapter(MovieList items) {
        this.items = items;
        Log.d("MovieAdapter", "Number of movies: " + items.getResults().size());
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movie,parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.titleTxt.setText(items.getResults().get(position).getTitle());

        String baseUrl = "https://image.tmdb.org/t/p/original";
        String posterPath = items.getResults().get(position).getPosterPath();
        String posterUrl = baseUrl + posterPath;

        Log.d("MovieAdapter", "Poster URL for position " + position + ": " + posterUrl);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(posterUrl)
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("id", items.getResults().get(position).getId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return items.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}














