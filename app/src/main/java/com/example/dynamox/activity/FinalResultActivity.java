package com.example.dynamox.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dynamox.databinding.ActivityResultBinding;


public class FinalResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;
    View mRoot;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        mRoot = binding.getRoot();
        mContext = mRoot.getContext();
        setContentView(mRoot);
        populateView();
        binding.buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalResultActivity.this, ScreenQuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void populateView() {
        int score = getIntent().getIntExtra("score", 0);
        binding.txtScore.setText(String.valueOf(score));
    }
}



