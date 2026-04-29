package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button buSignUp,buLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.buSignUp=(Button) findViewById(R.id.buSignUp);
        this.buLogIn=(Button) findViewById(R.id.buLogIn);
        this.buSignUp.setOnClickListener(this);
        this.buLogIn.setOnClickListener(this);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v==buSignUp)
        {
           Intent GoSignUp=new Intent(MainActivity.this, SignUp.class);
           startActivity(GoSignUp);
        }
        if (v==buLogIn)
        {
            Intent GoLogIn=new Intent(MainActivity.this, LogIn.class);
            startActivity(GoLogIn);
        }
    }
}