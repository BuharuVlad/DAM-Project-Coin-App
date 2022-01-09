package util;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConvertor {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    //static deoarece dorim marcam ca aceste metode nu tin de obiectele acestei clase
    //se pot apela fara sa fie nevoie sa cream un obiect de acest
    @TypeConverter
    public static Date fromString(String value){
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
    @TypeConverter
    public static String fromDate(Date value){
        if(value == null){
            return null;
        }
        return formatter.format(value);
    }
}
