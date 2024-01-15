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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assessment_movie.Adapters.MovieAdapter;
import com.example.assessment_movie.Domain.MovieList;
import com.example.assessment_movie.utils.NetworkChangeReceiver;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver;
    RecyclerView.Adapter adapterBestMovies, adapterPopular, adapterRecent;
    private RecyclerView recyclerViewBestMovies, recyclerViewPopular, recyclerViewRecent;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3;
    private ProgressBar progressBar1, progressBar2, progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Network Connectivity check initialization inside the main page
        networkChangeReceiver = new NetworkChangeReceiver(this);

        //Initialization of the 3 recycler views in the main activity page
        recyclerViewBestMovies = findViewById(R.id.viewBest);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewPopular = findViewById(R.id.viewPopular);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewRecent = findViewById(R.id.viewRecent);
        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        LinearLayout myLinearLayout = findViewById(R.id.favMain);
        myLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });


        sendRequestBest();
        sendRequestPopular();
        sendRequestRecent();
    }

    @Override
    protected void onResume() {
        super.onResume();
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


    private void sendRequestBest() {
        progressBar1 = findViewById(R.id.progressBar1);
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar1.setVisibility(View.VISIBLE);

        //API request to get all movies
        mStringRequest = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/", response -> {
            Gson gson = new Gson();
            progressBar1.setVisibility(View.GONE);
            MovieList items = gson.fromJson(response, MovieList.class);
            Log.d("MovieAdapter", "Number of movies: " + items.getResults().size());

            //Sorting the movies by vote average in descending order
            items.getResults().sort((m1, m2) -> Double.compare(m2.getVoteAverage(), m1.getVoteAverage()));

            adapterBestMovies = new MovieAdapter(items);
            recyclerViewBestMovies.setAdapter(adapterBestMovies);
        }, error -> {
            progressBar1.setVisibility(View.GONE);
            Log.i("TAG", "onErrorResponse: " + error.toString());
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    private void sendRequestPopular() {
        progressBar2 = findViewById(R.id.progressBar2);
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar2.setVisibility(View.VISIBLE);

        mStringRequest2 = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/", response -> {
            Gson gson = new Gson();
            progressBar2.setVisibility(View.GONE);
            MovieList items = gson.fromJson(response, MovieList.class);
            Log.d("MovieAdapter", "Number of movies: " + items.getResults().size());

            //Sorting the movies by popularity score
            items.getResults().sort((m1, m2) -> Double.compare(m2.getPopularity(), m1.getPopularity()));

            adapterPopular = new MovieAdapter(items);
            recyclerViewPopular.setAdapter(adapterPopular);
        }, error -> {
            progressBar2.setVisibility(View.GONE);
            Log.i("TAG", "onErrorResponse: " + error.toString());
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        mRequestQueue.add(mStringRequest2);
    }

    private void sendRequestRecent() {
        progressBar3 = findViewById(R.id.progressBar3);
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar3.setVisibility(View.VISIBLE);

        mStringRequest3 = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/", response -> {
            Gson gson = new Gson();
            progressBar3.setVisibility(View.GONE);
            MovieList items = gson.fromJson(response, MovieList.class);
            Log.d("MovieAdapter", "Number of movies: " + items.getResults().size());

            //Sorting the movies by release date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            items.getResults().sort((m1, m2) -> {
                try {
                    return Objects.requireNonNull(sdf.parse(m2.getReleaseDate())).compareTo(sdf.parse(m1.getReleaseDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            });

            adapterRecent = new MovieAdapter(items);
            recyclerViewRecent.setAdapter(adapterRecent);
        }, error -> {
            progressBar3.setVisibility(View.GONE);
            Log.i("TAG", "onErrorResponse: " + error.toString());
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        mRequestQueue.add(mStringRequest3);
    }
}
