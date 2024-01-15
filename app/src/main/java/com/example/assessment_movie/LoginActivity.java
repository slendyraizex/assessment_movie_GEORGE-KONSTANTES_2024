package com.example.assessment_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assessment_movie.utils.NetworkChangeReceiver;

public class LoginActivity extends AppCompatActivity {
    private NetworkChangeReceiver networkChangeReceiver;

    private EditText userEdt, passEdt;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        networkChangeReceiver = new NetworkChangeReceiver(this);

        initView();
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

    private void initView(){
        userEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEdt.getText().toString().isEmpty() || passEdt.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill out your username and password", Toast.LENGTH_SHORT).show();
                } else if (userEdt.getText().toString().equals("test") && passEdt.getText().toString().equals("test")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Your username and/or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}