package com.findme;

import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) throws Exception {
        Pattern urlPattern = Pattern.compile("^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");

        String msg = "tutu n 4.55 tk. dkdsk. ccc.uf ;l;'sd.";
        String[] words = msg.split(" ");

        for(String word : words){
            System.out.println(word);
            if(word.contains("http") || urlPattern.matcher(word).matches()) {
                System.out.print(" - gotcha");
                return;
            }
        }




    }
}
