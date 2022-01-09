package database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "coins")
public class Coin  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "value")
    private Double value;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Coin(long id, String name, Double value, Date date, byte[] image) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.date = date;
        for(int i = 0; i < image.length; i++){
            this.image[i] = image[i];
        }
    }

    @Ignore
    public Coin(String name, Double value, Date date) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    @Ignore
    public Coin(String name, Double value, Date date, byte[] image) {
        this.name = name;
        this.value = value;
        this.date = date;
        this.image = image;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", date=" + date +
                ", image='" + image + '\'' +
                '}';
    }
}
