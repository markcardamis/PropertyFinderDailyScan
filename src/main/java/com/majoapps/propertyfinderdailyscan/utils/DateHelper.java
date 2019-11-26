package com.majoapps.propertyfinderdailyscan.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class DateHelper {
    public boolean isBusinessDay(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        Integer hours = (cal.get(Calendar.HOUR_OF_DAY));

        // check if weekend
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return false;
        } else if (( hours < 17) || (hours >= 18)){ //only run between 5 - 6 pm
            return false;
        }
        // IF NOTHING ELSE, IT'S A BUSINESS DAY
        return true;
    }
}
