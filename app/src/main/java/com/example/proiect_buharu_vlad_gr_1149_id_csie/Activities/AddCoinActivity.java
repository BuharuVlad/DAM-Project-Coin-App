package com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.*;

import async.Callback;
import database.Coin;
import database.CoinService;
import util.DateConvertor;
import util.ImageConvertor;

public class AddCoinActivity extends AppCompatActivity {

    public static final String COIN_KEY = "coin_key";
    private TextInputEditText tietNameCoin;
    private TextInputEditText tietValueCoin;
    private TextInputEditText tietDateCoin;
    private DatePickerDialog.OnDateSetListener setListener;
    private Button btnSave;
    private CoinService coinService;
    private Coin coin;
    private List<Coin> coins = new ArrayList<>();

    private static final int PICK_IMAGE = 100;
    private Uri imageFilePath;
    private ImageView imageView;
    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coin);

        //initializare coinService;
        coinService = new CoinService(getApplicationContext());

        //initializarea componentelor din activitate
        initComponents();

        //preluarea datei din DatePiker
        takeDateFromDatePiker();

        //inserarea monedei in baza de date
        insertInDB();

        intent = getIntent();

        if(intent.hasExtra(COIN_KEY)){
            //facem diferita dintre update si insert
            coin = (Coin) intent.getSerializableExtra(COIN_KEY);
            createViewsFromCoin();
        }

    }

    private void createViewsFromCoin() {
        if(coin ==null){
            return;
        }
        tietNameCoin.setText(coin.getName());
        tietValueCoin.setText(String.valueOf(coin.getValue()));
        tietDateCoin.setText(DateConvertor.fromDate(coin.getDate()));
    }

    private void insertInDB() {
        coinService.insert(coin, insertCoinCallback());
    }

    private Callback<Coin> insertCoinCallback() {
        return new Callback<Coin>() {
            @Override
            public void runResultOnUiThread(Coin coin) {
                if(coin != null){
                    coins.add(coin);
                }
            }
        };
    }

    /* Utilizarea DatePiker, preluarea datei din acesta */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void takeDateFromDatePiker() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tietDateCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddCoinActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
                setListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day +"/"+ month+"/"+year;
                        tietDateCoin.setText(date);
                    }
                };
    }
    /* Utilizarea DatePiker, preluarea datei din acesta */

    /* Initializarea componentelor din activitate */
    private void initComponents() {
        tietNameCoin = findViewById(R.id.tiet_name_of_coin);
        tietValueCoin = findViewById(R.id.tiet_value_of_coin);
        tietDateCoin = findViewById(R.id.tiet_date_of_coin);
        imageView = findViewById(R.id.imageView2);
        btnSave = findViewById(R.id.btn_add_coin_save);
        btnSave.setOnClickListener(getSaveCoinClickListener());
    }
    /* Initializarea componentelor din activitate */


    private View.OnClickListener getSaveCoinClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    coin = buildCoinFromComponent();
                    insertInDB();
                    Log.i("AddCoin", coin.toString());
                    intent.putExtra(COIN_KEY, coin);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private boolean isValid() {
        if(tietNameCoin.getText() == null ||
        tietNameCoin.getText().toString().trim().length() < 3){
                Toast.makeText(getApplicationContext(),
                        R.string.add_coin_name_error_message,
                        Toast.LENGTH_LONG).show();
                tietNameCoin.requestFocus();
                return false;
        }
        if (tietValueCoin.getText() == null ||
                tietValueCoin.getText().toString().trim().isEmpty() ||
                Float.parseFloat(tietValueCoin.getText().toString().trim()) < 0 ) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_coin_value_error_message,
                    Toast.LENGTH_LONG).show();
            tietValueCoin.requestFocus();
            return false;
        }
        if(tietDateCoin.getText() == null ||
                DateConvertor.fromString(tietDateCoin.getText().toString().trim()) == null){
            Toast.makeText(getApplicationContext(),
                    "Invalid date. Accepted format dd/MM/yyyy",
                    Toast.LENGTH_LONG).show();
            tietDateCoin.requestFocus();
            return false;
        }
        return true;
    }

    private Coin buildCoinFromComponent() {
        String name = tietNameCoin.getText().toString();
        Double value = Double.parseDouble(tietValueCoin.getText().toString());
        Date date = DateConvertor.fromString(tietDateCoin.getText().toString());
        byte[] imageArray = ImageConvertor.convertImageToArray(imageView);
        if(coin == null){
            coin = new Coin(name, value, date, imageArray);
        } else{
            coin.setName(name);
            coin.setValue(value);
            coin.setDate(date);
            coin.setImage(imageArray);
        }
        return coin;
    }


    public void chooseImage(View objectView){
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE);
        } catch (Exception e){
            Log.e("AddCoinActivity", e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode ==PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
                imageFilePath = data.getData();
                imageView.setImageURI(imageFilePath);
            }
        } catch (Exception e){
            Log.e("AddCoinActivity", e.getMessage());
        }
    }

}