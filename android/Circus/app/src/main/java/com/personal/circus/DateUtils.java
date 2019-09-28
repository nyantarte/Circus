package com.personal.circus;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static boolean isHolyday(Calendar c){
        int dWeek= c.get(Calendar.DAY_OF_WEEK);
        if(Calendar.SUNDAY==dWeek || Calendar.SATURDAY==dWeek){
            return true;
        }

        return false;
    }

    public static Calendar adjustHolyday(Calendar c){
        while(isHolyday(c)){
            c.add(Calendar.DAY_OF_MONTH,-1);
        }
        return c;
    }
}
