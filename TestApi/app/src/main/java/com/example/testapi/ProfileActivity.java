package com.example.testapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtName, txtEmail;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        btnLogout = findViewById(R.id.btnLogout);

        txtName.setText(SharedPrefManager.getInstance(this).getUsername());
        txtEmail.setText(SharedPrefManager.getInstance(this).getEmail());

        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
    }
}