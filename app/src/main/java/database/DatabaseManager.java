package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import util.DateConvertor;

@Database(entities = {Coin.class},exportSchema = false,version = 1)
@TypeConverters({DateConvertor.class})
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context){
        if(databaseManager == null){
            synchronized (DatabaseManager.class){
                if(databaseManager == null){
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "coins_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract CoinDao getCoinDao();
}
