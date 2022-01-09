package util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.Coin;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CoinJsonParser {
    public static List<Coin> fromJson(String json){
            try {
                JSONObject objectJson = new JSONObject(json);
                return readCoinsFromJson(objectJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
    }

    private static List<Coin> readCoinsFromJson(JSONObject objectJson) {
        try {
            JSONArray coinsArray = objectJson.getJSONArray("coins");
            return readCoinsFromArray(coinsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Coin> readCoinsFromArray(JSONArray coinsArray) throws JSONException {
        List<Coin> results = new ArrayList<>();
        for(int i = 0; i < coinsArray.length(); i++){
            JSONObject object = coinsArray.getJSONObject(i);
            Coin coin = readCoinfromObject(object);
            results.add(coin);
        }
        return  results;
    }

    private static Coin readCoinfromObject(JSONObject object) throws JSONException {
        String name = object.getString("name");
        double value = object.getDouble("value");
        String dateString = object.getString("date");
        Date date = DateConvertor.fromString(dateString);

        return new Coin(name, value, date);
    }


}
