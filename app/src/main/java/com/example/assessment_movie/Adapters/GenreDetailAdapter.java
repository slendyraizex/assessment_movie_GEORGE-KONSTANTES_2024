package com.example.assessment_movie.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment_movie.DetailDomain.Genre;
import com.example.assessment_movie.R;

import java.util.List;

public class GenreDetailAdapter extends RecyclerView.Adapter<GenreDetailAdapter.GenreViewHolder> {

    private List<Genre> genreList;

    public GenreDetailAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_genre, parent, false);

        return new GenreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.genreName.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        public TextView genreName;

        public GenreViewHolder(View view) {
            super(view);
            genreName = view.findViewById(R.id.genreView);
        }
    }
}

