package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {


    private MainViewModel mainViewModel;

    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getUserLiveData().observe(this, user -> {
            if(user != null) {
                // User logged in
                binding.userName.setText("Hello " + user.getName() + " !");
            }
            else {
                // User Logged out
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }
}
