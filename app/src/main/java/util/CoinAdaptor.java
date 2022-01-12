package util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.R;

import java.util.Date;
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
        addCoinDate(view, coin.getDate());
        return view;
    }

    private void addCoinDate(View view, Date date) {
        TextView textView = view.findViewById(R.id.row_iv_date);
        if(date !=null){
            textView.setText(DateConvertor.fromDate(date));
        }else{
            textView.setText("00-00-0000");
        }
    }


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
