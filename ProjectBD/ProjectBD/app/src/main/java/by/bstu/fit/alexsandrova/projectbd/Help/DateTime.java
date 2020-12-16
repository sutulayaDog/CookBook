package by.bstu.fit.alexsandrova.projectbd.Help;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    public static int getDateSpecialFormat(Date date){
        String pattern = "yyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return Integer.valueOf(simpleDateFormat.format(date));
    }
    public static int getTimeSpecialFormat(Date date){
        String pattern = "HHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return Integer.valueOf(simpleDateFormat.format(date));
    }

    public static Date addMinutes(Date date, int minutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }
}

