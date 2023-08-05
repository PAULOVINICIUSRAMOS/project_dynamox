package com.example.dynamox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dynamox.activity.SupportActivity;
import com.example.dynamox.controller.DatabaseHelper;
import com.example.dynamox.data.database.DatabaseConfig;
import com.example.dynamox.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseConfig db;
    View mRoot;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mRoot = binding.getRoot();
        mContext = mRoot.getContext();
        setContentView(mRoot);

        saveNameUser();
    }

    private void saveNameUser() {
        final DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                databaseHelper.saveUser(name);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.editTextName.setText("");
                    }
                });

                Intent intent = new Intent(MainActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });
    }
}