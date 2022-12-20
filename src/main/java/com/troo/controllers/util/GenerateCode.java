// By: Suchir
// Sprint: 3
package com.troo.controllers.util;
import java.io.*;
public class GenerateCode {
    // 2 return functions. 1 to generate a 6 digit code, and 1 to generate 18
    // alphanumeric character code (Ju89lL5258K78LGrT7), they cannot be the same as
    // any of the other one in the other file (Transactions.txt)
    // "C:/Users/suchi/OneDrive/Documents/troo/src/main/resources/com/troo/data/transactions.txt"


    public static String receiptCode() {
        //declare variables
        String code = "";
        String line = "";
        boolean sameNum = false;
        try{
            //create file reader and buffered reader
            FileReader fr = new FileReader("C:/Users/suchi/OneDrive/Documents/troo/src/main/resources/com/troo/data/transactions.txt");
            BufferedReader br = new BufferedReader(fr);

            do{
                //generate 6 digit code
                for (int i = 0; i < 6; i++) {
                    code += (int)(9 * Math.random());  // add a random digit (0-9) to the code
                }
                //read first line in file
                line = br.readLine();
                
                while(line!=null){
                    //read next 2 lines
                    line = br.readLine();
                    line = br.readLine();
                    //substring every third line and compare it to the code
                    line = line.substring(8,15);
                    //if codes are the same boolean set to false and do while loop will repeat
                    if(code.equals(line)){
                        sameNum = false;
                    }
                    //if code is unique then code is returned 
                    else{
                        sameNum = true;
                        return code;
                    }
                    line = br.readLine();
                }
            }while(!sameNum);
            //closing br
            br.close();//should close
        }catch(IOException err){
            System.out.println("Error reading from the file");
            err.printStackTrace();
        }
        
        return code;
    }


    //this method will generate an 18 digit alphanumeric code 
    public static String transactionCode() {
        char[] code = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        String alphaCode ="";

        for (int i = 0; i < 18; i++) {
            int index = (int) (62 * Math.random());
            alphaCode+=(code[index]);
        }
        return alphaCode;
    }


}