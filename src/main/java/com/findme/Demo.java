package com.findme;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) throws Exception {

        int[] array = {1,34,2,5,87,32,5,9,0};
        System.out.println(Arrays.toString(array));

        for(int i = 0; i < array.length; i++){
            for(int j = i+1; j < array.length; j++){
                if(array[j] < array[i]){
                    int min = array[i];
                    array[i] = array[j];
                    array[j] = min;
                }
            }
        }
        System.out.println(Arrays.toString(array));


        for(int i = array.length-1; i >= 0; i--){
            for(int j = i-1; j >= 0; j--){
                if(array[i] > array[j]){
                    int max = array[j];
                    array[j] = array[i];
                    array[i] = max;
                }
            }
        }
        System.out.println(Arrays.toString(array));

    }

}

