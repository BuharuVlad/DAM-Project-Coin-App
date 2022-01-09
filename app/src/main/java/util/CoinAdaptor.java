package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import database.Coin;

public class CoinAdaptor extends ArrayAdapter<Coin> {

    private Context  context;
    private int resource;
    private List<Coin> coins;
    private LayoutInflater inflater;

    public CoinAdaptor(@NonNull Context context, int resource, @NonNull List<Coin> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.coins = objects;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Coin coin = coins.get(position);
        if(coin == null){
            return  view;
        }
        addCoinName(view, coin.getName());
        addCoinValue(view, coin.getValue());
//        addCoinImage(view, coin.getImage());
        return view;
    }
//
//    private void addCoinImage(View view, byte[] byteArray) {
//        try{
//        if(byteArray != null && byteArray.length > 0){
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            ImageView image = (ImageView) view.findViewById(R.id.row_iv_picture);
//            image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
//        }
//        else{
//            Log.e("CoinAdaptor", "Can't convert the image");
//        }} catch (Exception e){
//            Log.e("CoinAdaptor", e.getMessage());
//        }
//    }


    private void addCoinValue(View view, Double value) {
        TextView textView = view.findViewById(R.id.row_tv_coin_value);
        if(value !=null && value > 0){
            textView.setText(value.toString());
        }else{
            textView.setText("0");
        }
    }

    private void addCoinName(View view, String name) {
        TextView textView = view.findViewById(R.id.row_tv_coin_name);
        if(name !=null && !name.trim().isEmpty()){
            textView.setText(name);
        }else{
            textView.setText(R.string.bank_name_adaptor_default_value);
        }
    }
}
