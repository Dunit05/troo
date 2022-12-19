// By: Suchir
// Sprint: 3

package com.troo.controllers.util;

public class GenerateCode {
    // 2 return functions. 1 to generate a 6 digit code, and 1 to generate 18
    // alphanumeric character code (Ju89lL5258K78LGrT7), they cannot be the same as
    // any of the other one in the other file (Transactions.txt)

     public int[] receiptCode() {
     int arr[];
     arr = new int[6];
     for (int i = 0; i < 6; i++) {
     arr[i] = (int) (9 * Math.random()) + 1;
     }
     return arr;
     }

    public static StringBuilder transactionCode() {
        char[] code = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder alphaCode = new StringBuilder();

        for (int i = 0; i < 18; i++) {
            int index = (int) (62 * Math.random());
            alphaCode.append(code[index]);
        }
        return alphaCode;
    }

    public static void checkCode() {
        // check if the code is in the file
        // if it is, generate a new one
        // if it isn't, return the code
    }

}