package database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import async.AsyncTaskRunner;
import async.Callback;

public class CoinService {


    //il folosim in toate activitatile
    private final CoinDao coinDao;
    private final AsyncTaskRunner asyncTaskRunner;

    //contructor
    public CoinService(Context context){
        this.coinDao = DatabaseManager.getInstance(context).getCoinDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    //insert
    public void insert(Coin coin, Callback<Coin> activityThread){
        Callable<Coin> insertOperation = new Callable<Coin>() {
            @Override
            public Coin call() {
                //realizarea operatiei de insert cu baza de date
                //ne aflam pe alt fir de executie
                if(coin == null || coin.getId() > 0){
                    //getId > 0  ca daca e mai mare ca 0 inseamna ca exista deja in baza de date
                    return null;
                }
                long id = coinDao.insert(coin);
                if(id < 0){
                    return null;
                }
                coin.setId(id);
                return coin;
            }
        };

        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }

    public void getAll(Callback<List<Coin>> activityThread) {
        Log.i("ListOfCoins", "getAll 1");
        Callable<List<Coin>> gellAllOperation = new Callable<List<Coin>>() {
            @Override
            public List<Coin> call() {

                Log.i("ListOfCoins", "getAll 2");
                return coinDao.getAll();
            }
        };

        Log.i("ListOfCoins", "getAll 3");
        asyncTaskRunner.executeAsync(gellAllOperation, activityThread);
    }

    public void update(Coin Coin, Callback<Coin> activityThread) {
        Callable<Coin> updateOperation = new Callable<Coin>() {
            @Override
            public Coin call() {
                if (Coin == null || Coin.getId() <= 0) {
                    return null;
                }
                int count = coinDao.update(Coin);
                if (count <= 0) {
                    return null;
                }
                return Coin;
            }
        };

        asyncTaskRunner.executeAsync(updateOperation, activityThread);
    }

    public void delete(Coin Coin, Callback<Boolean> activityThread) {
        Callable<Boolean> deleteOperation = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                if (Coin == null || Coin.getId() <= 0) {
                    return false;
                }
                int count = coinDao.delete(Coin);
                return count > 0;
            }
        };

        asyncTaskRunner.executeAsync(deleteOperation, activityThread);
    }

    private <R> void sendDatabaseResponseToActivityThread(R result, Callback<R> activityThread) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                activityThread.runResultOnUiThread(result);
            }
        });
    }

}
