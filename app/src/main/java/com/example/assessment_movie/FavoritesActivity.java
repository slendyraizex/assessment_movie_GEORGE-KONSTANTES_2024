package com.example.assessment_movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assessment_movie.Adapters.MovieAdapter;
import com.example.assessment_movie.Domain.Favorites;
import com.example.assessment_movie.Domain.MovieList;
import com.example.assessment_movie.Domain.Result;
import com.example.assessment_movie.utils.NetworkChangeReceiver;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RecyclerView.Adapter adapterFavorites;
    private RecyclerView recyclerFavorites;
    private ImageView backImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        networkChangeReceiver = new NetworkChangeReceiver(this);

        backImg = findViewById(R.id.backBtnFav);
        backImg.setOnClickListener(v -> finish());

        recyclerFavorites = findViewById(R.id.viewFavorite);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        LinearLayout myLinearLayout = findViewById(R.id.exploreMain);
        myLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Result> favoriteMoviesList = Favorites.getInstance().getFavoriteMovies();
        MovieList favoriteMovies = new MovieList();
        favoriteMovies.setResults(favoriteMoviesList);
        adapterFavorites = new MovieAdapter(favoriteMovies);
        recyclerFavorites.setAdapter(adapterFavorites);

        //Internet Connection check
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }
}

