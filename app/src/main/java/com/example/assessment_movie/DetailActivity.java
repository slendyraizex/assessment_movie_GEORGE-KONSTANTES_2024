package com.example.assessment_movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.assessment_movie.Adapters.ActorDetailAdapter;
import com.example.assessment_movie.Adapters.CrewDetailAdapter;
import com.example.assessment_movie.Adapters.GenreDetailAdapter;
import com.example.assessment_movie.DetailDomain.Cast;
import com.example.assessment_movie.DetailDomain.CastResponse;
import com.example.assessment_movie.DetailDomain.Crew;
import com.example.assessment_movie.DetailDomain.CrewResponse;
import com.example.assessment_movie.DetailDomain.Genre;
import com.example.assessment_movie.DetailDomain.MovieDetail;
import com.example.assessment_movie.Domain.Favorites;
import com.example.assessment_movie.Domain.Result;
import com.example.assessment_movie.utils.NetworkChangeReceiver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private NetworkChangeReceiver networkChangeReceiver;
    private RequestQueue mRequestQueue;
    private RecyclerView.Adapter adapterGenre, adapterActorList, adapterCrewList;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3, mStringRequest4;
    private TextView movieTitleTxt, movieStarTxt, movieTimeTxt, movieOverviewTxt, movieReleaseDateTxt;
    private RecyclerView recyclerViewGenre, recyclerViewActors, recyclerViewCrew;
    private ProgressBar progressBar;
    private ImageView pic2, backImg, favImg;
    private Result movie;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Network Connectivity check initialization inside the detail page
        networkChangeReceiver = new NetworkChangeReceiver(this);

        movieTitleTxt = findViewById(R.id.movieName);

        int movieId = getIntent().getIntExtra("id", 0);
        movie = new Result();
        movie.setId(movieId);

        recyclerViewGenre = findViewById(R.id.genreView);
        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewActors = findViewById(R.id.actorsView);
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCrew = findViewById(R.id.crewView);
        recyclerViewCrew.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCrew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        backImg = findViewById(R.id.backBtn);
        backImg.setOnClickListener(v -> finish());

        //Initialization of share button and its code it should send the movie's poster as well as some plain text
        shareButton = findViewById(R.id.shareBtn);
        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(pic2)));
            String shareBody = "Check out this movie: " + movieTitleTxt.getText().toString();
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Recommendation");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        //Initialization and functionality of favorite button
        favImg = findViewById(R.id.favBtn);
        if (Favorites.getInstance().isFavorite(movie)) {
            favImg.setImageResource(R.drawable.fav_full);
        } else {
            favImg.setImageResource(R.drawable.fav_empty);
        }

        getMovieDetails(movieId);
        favImg.setOnClickListener(v -> {
            if (Favorites.getInstance().isFavorite(movie)) {
                Favorites.getInstance().removeMovie(movie);
                // update favorite button UI to indicate movie is not favorited
                favImg.setImageResource(R.drawable.fav_empty);
            } else {
                Favorites.getInstance().addMovie(movie);
                // update favorite button UI to indicate movie is favorited
                favImg.setImageResource(R.drawable.fav_full);
            }
        });



        movieTitleTxt = findViewById(R.id.movieName);
        movieStarTxt = findViewById(R.id.movieStar);
        movieTimeTxt = findViewById(R.id.movieTime);
        movieOverviewTxt = findViewById(R.id.movieOverview);
        movieReleaseDateTxt = findViewById(R.id.movieDate);


        progressBar = findViewById(R.id.progressBarDetail);
        pic2 = findViewById(R.id.picDetail);

        sendRequest(movieId);

        sendRequestGenre(movieId);

        sendRequestActors(movieId);

        sendRequestCrew(movieId);

    }

    private void getMovieDetails(int movieId) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://app-vpigadas.herokuapp.com/api/movies/" + movieId;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        // Parse the response
                        Gson gson = new Gson();
                        Result movie = gson.fromJson(response, Result.class);
                    },
                    error -> {
                        // Handle the error
                        Log.e("TAG", "Error: " + error.getMessage());
                    });

            requestQueue.add(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check if movie has been favorited or not
        if (Favorites.getInstance().isFavorite(movie)) {
            favImg.setImageResource(R.drawable.fav_full);
        } else {
            favImg.setImageResource(R.drawable.fav_empty);
        }
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



    private void sendRequest(int movieId) {
        mRequestQueue = Volley.newRequestQueue(this);

        mStringRequest = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/" + movieId, response -> {
            Gson gson = new Gson();
            MovieDetail item = gson.fromJson(response, MovieDetail.class);
            movieTitleTxt.setText(item.getTitle());



            //Converting the double value of the voteAverage into a string with a single decimal for a better visual
            double voteAverage = item.getVoteAverage();
            @SuppressLint("DefaultLocale") String formattedVoteAverage = String.format("%.1f", voteAverage);
            movieStarTxt.setText(formattedVoteAverage);

            movieTimeTxt.setText(String.valueOf(item.getRuntime() + "'"));
            movieOverviewTxt.setText(item.getOverview());
            movieReleaseDateTxt.setText(item.getReleaseDate());

            List<Cast> castList = item.getCast();
            StringBuilder actors = new StringBuilder();
            for (Cast cast : castList) {
                actors.append(cast.getName()).append(", ");
            }



            Glide.with(DetailActivity.this).load(item.getPosterPath()).into(pic2);
        }, error -> {
            progressBar.setVisibility(View.GONE);
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

    private void sendRequestGenre(int movieId) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/" + movieId, response -> {
            Gson gson = new Gson();
            MovieDetail item = gson.fromJson(response, MovieDetail.class);
            List<Genre> genres = item.getGenres();
            adapterGenre = new GenreDetailAdapter(genres);
            recyclerViewGenre.setAdapter(adapterGenre);
        }, error -> {
            progressBar.setVisibility(View.GONE);
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

    private void sendRequestActors(int movieId) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest3 = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/" + movieId, response -> {
            Gson gson = new Gson();

            CastResponse castResponse = gson.fromJson(response, CastResponse.class);
            List<Cast> actors = castResponse.getActors();
            List<String> actorImages = new ArrayList<>();
            for (Cast cast : actors) {
                actorImages.add(cast.getProfilePath());
            }

            adapterActorList = new ActorDetailAdapter(actorImages, actors);
            recyclerViewActors.setAdapter(adapterActorList);
        }, error -> {
            progressBar.setVisibility(View.GONE);
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


    private void sendRequestCrew(int movieId) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest4 = new StringRequest(Request.Method.GET, "https://app-vpigadas.herokuapp.com/api/movies/" + movieId, response -> {
            Gson gson = new Gson();

            CrewResponse crewResponse = gson.fromJson(response, CrewResponse.class);
            List<Crew> crewMembers = crewResponse.getCrew();
            List<String> crewImages = new ArrayList<>();
            for (Crew crew : crewMembers) {
                crewImages.add(crew.getProfilePath());
            }


            adapterCrewList = new CrewDetailAdapter(crewImages, crewMembers);
            recyclerViewCrew.setAdapter(adapterCrewList);
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Log.i("TAG", "onErrorResponse: " + error.toString());
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        mRequestQueue.add(mStringRequest4);
    }





}
