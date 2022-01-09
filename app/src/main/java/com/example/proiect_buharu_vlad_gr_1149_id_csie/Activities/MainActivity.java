package com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;

import util.ProgressBarAnimation;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textViewProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_horizontal);
        textViewProgressBar = findViewById(R.id.tv_progress_bar);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();
    }
    public void progressAnimation() {
        ProgressBarAnimation animation = new ProgressBarAnimation(this, progressBar, textViewProgressBar,0,100);
        animation.setDuration(8000);
        progressBar.setAnimation(animation);
    }
}