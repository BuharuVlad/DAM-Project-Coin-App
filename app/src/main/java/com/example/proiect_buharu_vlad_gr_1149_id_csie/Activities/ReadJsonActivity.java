package com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import database.Coin;
import util.DateConvertor;

public class ReadJsonActivity extends AppCompatActivity {
    private ListView listViewReadJson;
    private List<Coin> coins;

    private static final String FILE_NAME = "fromJson.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_json);

        initComponent();

    }

    public void save(View v){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < listViewReadJson.getCount();i++){
            stringBuilder.append(listViewReadJson.getItemAtPosition(i));
            stringBuilder.append("\n");
        }
        String text = String.valueOf(stringBuilder);
        Log.i("ReadJsonActivity", "stringBuilder: " + stringBuilder);
        Log.i("ReadJsonActivity", "text: " + text);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Saved to " + getFilesDir() +"/"+ FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }


    private void initComponent() {

        listViewReadJson = findViewById(R.id.lv_read_json_activity);
        coins = new ArrayList<>();

        try{
            JSONObject objectJson=new JSONObject(LoadJsonFromAsset());
            JSONArray array=objectJson.getJSONArray("coins");
            HashMap<String,String> list;
            ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
            for( int i=0;i<array.length();i++){
                JSONObject coin = array.getJSONObject(i);
                String name = coin.getString("name");
                Double value = coin.getDouble("value");
                String dateString = coin.getString("date");
                Date date = DateConvertor.fromString(dateString);
                list=new HashMap<>();
                list.put("name",name);
                list.put("value",value.toString());
                list.put("date",DateConvertor.fromDate(date));
                arrayList.add(list);
            }

            ListAdapter adapter=new SimpleAdapter(
                    this,
                    arrayList,
                    R.layout.lv_design_read_json,
                    new String[]{"name","value","date"},
                    new int[]{
                            R.id.desing_tv_coin_name,
                            R.id.desing_tv_coin_value,
                            R.id.desing_tv_coin_date});
            listViewReadJson.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String LoadJsonFromAsset(){
        String json=null;
        try{
            InputStream in=this.getAssets().open(getString(R.string.json_file));
            int size=in.available();
            byte[] bbuffer=new byte[size];
            in.read(bbuffer);
            in.close();
            json=new String(bbuffer, StandardCharsets.UTF_8);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}