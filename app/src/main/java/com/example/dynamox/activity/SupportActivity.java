package com.example.dynamox.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamox.databinding.ActivitySupportBinding;

public class SupportActivity extends AppCompatActivity {

    private ActivitySupportBinding binding;

    View mRoot;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());
        mRoot = binding.getRoot();
        mContext = mRoot.getContext();
        setContentView(mRoot);

        binding.startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportActivity.this, ScreenQuestionsActivity.class);
                startActivity(intent);
            }
        });

    }
}
