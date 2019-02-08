package com.findme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {
    public static void main(String[] args) throws Exception {

        Date datePosted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2019-02-08T10:37:19Z");

//        getDaysAgo(datePosted);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        System.out.println(sdf.format(date));


    }

    public static String getDaysAgo(Date datePosted){
        StringBuffer result = new StringBuffer();
        Date currentDate = new Date();
        long diff = currentDate.getTime()-datePosted.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);

        System.out.println(diff);
        System.out.println(diffSeconds);
        System.out.println(diffMinutes);
        System.out.println(diffHours);

//        if(diffSeconds < 60)
//            result.append(diffSeconds).append(" sec.");
//        if()

        return null;
    }
}
