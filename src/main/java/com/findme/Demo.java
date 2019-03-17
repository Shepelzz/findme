package com.findme;

import com.findme.test.ConversationInfo;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {
    private static Logger log = Logger.getLogger(Demo.class.getName());


    public static void main(String[] args) throws Exception {


        ConversationInfo v;

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-01-01 15:51:11")  );


//        int[] array = {1,34,2,5,87,32,5,9,0};
////        System.out.println(Arrays.toString(array));
//
//        for(int i = 0; i < array.length; i++){
//            for(int j = i+1; j < array.length; j++){
//                if(array[j] < array[i]){
//                    int min = array[i];
//                    array[i] = array[j];
//                    array[j] = min;
//                }
//            }
//        }
////        System.out.println(Arrays.toString(array));
//
//
//        for(int i = array.length-1; i >= 0; i--){
//            for(int j = i-1; j >= 0; j--){
//                if(array[i] > array[j]){
//                    int max = array[j];
//                    array[j] = array[i];
//                    array[i] = max;
//                }
//            }
//        }
////        System.out.println(Arrays.toString(array));
//
//
//        int currentListPart = 3;
//        int maxResults = 10;
//
//        int rowsfrom, rowsTo;
//        rowsfrom = currentListPart == 1 ? 0 : currentListPart*maxResults-maxResults;
//        rowsTo = rowsfrom+maxResults;
//
//        System.out.println("from: "+rowsfrom+" to: "+rowsTo);



            log.info("start...");
        myMethod();

    }

    private static void myMethod(){
        try {
            String eee = "34eee";
            Long i = Long.valueOf(eee);

        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

}

