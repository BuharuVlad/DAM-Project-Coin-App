package com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import async.Callback;
import database.Coin;
import database.CoinService;
import network.HttpManager;
import util.CoinAdaptor;
import util.CoinJsonParser;

public class ListOfCoinsActivity extends AppCompatActivity {
    private ListView lvListOfCoins;
    List<Coin> coins = new ArrayList<>();
    private FloatingActionButton floatingActionButtonAddCoin;
    private ActivityResultLauncher<Intent> addCoinLauncher;
    private ActivityResultLauncher<Intent> updateCoinLauncher;
    private CoinService coinService;

    private static final String COIN_URL="https://jsonkeeper.com/b/FYWR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_coins);

        initComponents();

        coinService = new CoinService(getApplicationContext());
        coinService.getAll(getAllCoinsCallback());

        updateCoinLauncher = getUpdateCoinLauncher();
        loadCoinsFromUrl();
    }

    /* JSON from INTERNET */
    public void loadCoinsFromUrl(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpManager manager = new HttpManager(COIN_URL);
                String result = manager.process();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mainThreadGetCoinsFromHttpCallback(result);
                    }
                });
            }
        };
        thread.start();
    }

    private void mainThreadGetCoinsFromHttpCallback(String result){
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        coins.addAll(CoinJsonParser.fromJson(result));
        notifyAdapter();
    }
    /* JSON from INTERNET */


    /* Initializarea componentelor din activitate */
    private void initComponents() {
        lvListOfCoins = findViewById(R.id.list_of_coins_lv_coin);
        floatingActionButtonAddCoin = findViewById(R.id.list_of_coins_fab_add_coin);

        addCoinAdaptor();
        floatingActionButtonAddCoin.setOnClickListener(getAddCoinClickListener());
        addCoinLauncher = getAddCoinLauncher();

        lvListOfCoins.setOnItemClickListener(getItemClickEvent());
    }




    /* Initializarea componentelor din activitate */


    /* Lansarea activitatii de adaugarea a unei monede in baza de date si in ListView-ul din aceasta activitate */
    private ActivityResultLauncher<Intent> getAddCoinLauncher() {
        ActivityResultCallback<ActivityResult> callback = getAddCoinActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    /* Callback pentru formular */
    private ActivityResultCallback<ActivityResult> getAddCoinActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Coin coin = (Coin) result.getData().getSerializableExtra(AddCoinActivity.COIN_KEY);
                    coinService.insert(coin, insertCoinCallBack());
                }
            }
        };
    }
    /* Callback pentru formular */

    private View.OnClickListener getAddCoinClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCoinActivity.class);
                addCoinLauncher.launch(intent);
            }
        };
    }
    /* Lansarea activitatii de adaugarea a unei monede in baza de date si in ListView-ul din aceasta activitate */



    /* Adaptorul */
    private void addCoinAdaptor() {
        CoinAdaptor adapter = new CoinAdaptor(getApplicationContext(), R.layout.lv_row_view, coins, getLayoutInflater());
        lvListOfCoins.setAdapter(adapter);
    }

    private void notifyAdapter(){
        ArrayAdapter adapter = (ArrayAdapter) lvListOfCoins.getAdapter();
        adapter.notifyDataSetChanged();
    }
    /* Adaptorul */


    /* Operatiuni pe baza de date*/
    /* Confirmarea ca s-a realizat cu succes inserarea in baza de date */
    private Callback<Coin> insertCoinCallBack() {
        return new Callback<Coin>() {
            @Override
            public void runResultOnUiThread(Coin coin) {
                if (coin != null) {
                    Toast.makeText(getApplicationContext(), R.string.main_new_coin_added_message, Toast.LENGTH_SHORT).show();
                    coins.add(coin);
                    notifyAdapter();
                }
            }
        };
    }
    /* Confirmarea ca s-a realizat cu succes inserarea in baza de date */

    /* preluarea tuturor monedelor din baza de date*/
    private Callback<List<Coin>> getAllCoinsCallback() {
        return new Callback<List<Coin>>() {
            @Override
            public void runResultOnUiThread(List<Coin> results) {
                if (results != null) {
                    coins.addAll(results);
                    notifyAdapter();
                }
            }
        };
    }
    /* preluarea tuturor monedelor din baza de date*/


    /* Update pentru o moneda */
    private ActivityResultLauncher<Intent> getUpdateCoinLauncher() {
        ActivityResultCallback<ActivityResult> callback = getUpdateCoinActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private AdapterView.OnItemClickListener getItemClickEvent() {
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddCoinActivity.class);
                intent.putExtra(AddCoinActivity.COIN_KEY, coins.get(position));
                updateCoinLauncher.launch(intent);
            }
        };
    }

    private ActivityResultCallback<ActivityResult> getUpdateCoinActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Coin coin = (Coin) result.getData().getSerializableExtra(AddCoinActivity.COIN_KEY);
                    //update in baza de date
                    coinService.update(coin, updateCoinCallback());
                }
            }
        };
    }

    /* Construire Obiect Coin dupa update */
    private Callback<Coin> updateCoinCallback() {
        return new Callback<Coin>() {

            @Override
            public void runResultOnUiThread(Coin result) {
                if (result != null) {
                    for (Coin expense : coins) {
                        if (expense.getId() == result.getId()) {
                            expense.setName(result.getName());
                            expense.setValue(result.getValue());
                            break;

                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }
    /* Construire Obiect Coin dupa update */

    /* Update pentru o moneda */
    /* Operatiuni pe baza de date*/

}