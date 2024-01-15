package com.example.assessment_movie.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkUtils networkUtils;
    private Context context;

    public NetworkChangeReceiver(Context context) {
        this.context = context;
        networkUtils = new NetworkUtils(context);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (!networkUtils.isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Internet Connection");
            builder.setMessage("Please check your internet connection and try again.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


}
