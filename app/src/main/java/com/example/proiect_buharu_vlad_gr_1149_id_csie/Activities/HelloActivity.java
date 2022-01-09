package com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;
import com.google.android.material.button.MaterialButton;

import database.CoinService;

public class HelloActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;

    private MaterialButton loginButton;
    private CoinService coinService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        coinService = new CoinService(getApplicationContext());

        initComponents();

        /* Lansarea activitatii MainMeniuActivity si validatea datelor pentru logare */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals(getString(R.string.admin)) && password.getText().toString().equals(getString(R.string.passwordAdmin))) {
                    Toast.makeText(HelloActivity.this, R.string.loginSuccessful, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainMeniuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HelloActivity.this, R.string.loginFailed, Toast.LENGTH_SHORT).show();
                }
            }
        });
        /* Lansarea activitatii MainMeniuActivity si validatea datelor pentru logare */
    }

    /* Initializarea componentelor din activitate */
    private void initComponents(){
        username = findViewById(R.id.et_main_activity_username);
        password = findViewById(R.id.et_main_activity_password);
        loginButton = findViewById(R.id.mb_main_activityloginButton);
    }
    /* Initializarea componentelor din activitate */
}