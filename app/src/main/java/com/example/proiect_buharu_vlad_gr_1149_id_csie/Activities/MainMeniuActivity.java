package com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;

public class MainMeniuActivity extends AppCompatActivity {
    private Button btnAddCoin;
    private Button btnListCoins;


    //pentru preluarea datelor din formular completat
    private ActivityResultLauncher<Intent> addStudentLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meniu);

        initComponents();

        /* Lansarea activitatii de adaugare a unei monede in baza de date */
        btnAddCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCoin = new Intent(getApplicationContext(), AddCoinActivity.class);
                startActivity(intentAddCoin);
            }
        });
        /* Lansarea activitatii de adaugare a unei monede in baza de date */


        /* Lansarea activitatii de afisarea a intregii baze de date de monede si afisarea unor monede dintr-un fisier JSON preluat dupa Internet */
        btnListCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListOfCoins = new Intent(getApplicationContext(), ListOfCoinsActivity.class);
                startActivity(intentListOfCoins);
            }
        });
        /* Lansarea activitatii de afisarea a intregii baze de date de monede si afisarea unor monede dintr-un fisier JSON preluat dupa Internet */
    }


    /* Initializarea componentelor din activitate */
    private void initComponents(){
        btnAddCoin = findViewById(R.id.btn_meniu_add_coin);
        btnListCoins = findViewById(R.id.btn_meniu_view_list_of_coins);
    }
    /* Initializarea componentelor din activitate */
}