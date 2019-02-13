package com.findme;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) throws Exception {
        int[] unsorted = {32, 39,21, 45, 23, 3};

//        System.out.println(Arrays.toString(unsorted));

        for(int i = unsorted.length-1 ; i > 0 ; i--) {
            for (int j = 0; j < i; j++) {
                if (unsorted[j] > unsorted[j + 1]) {
                    int tmp = unsorted[j];
                    unsorted[j] = unsorted[j + 1];
                    unsorted[j + 1] = tmp;
                }
            }
        }

        System.out.println(Arrays.toString(unsorted));






    }

}

