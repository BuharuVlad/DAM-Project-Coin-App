package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoinDao {

    @Insert
    long insert(Coin coin);

    @Query("select * from coins")
    List<Coin> getAll();

    @Update
    int update(Coin coin);

    @Delete
    int delete(Coin coin);

}
