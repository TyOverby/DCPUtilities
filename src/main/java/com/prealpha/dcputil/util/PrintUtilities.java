package com.prealpha.dcputil.util;

import com.prealpha.dcputil.info.Operator;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 3:48 AM
 */
public class PrintUtilities {
    public static String convertHex(char value){
        final int MAX_LENGTH = 4;
        String converted = Integer.toHexString(value);
        while(converted.length()<4){
            converted = "0"+converted;
        }
        return "0x"+converted;
    }

    public static String convertDec(char value){
        return (int) value + "";
    }

    public static String convertBin(char value){
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(value));
        while(sb.length()<16){
            sb.insert(0,"0");
        }
        sb.insert(8," ");
        return sb.toString();
    }

    public static String convertOp(char value){
        StringBuilder sb = new StringBuilder(convertBin(value).replace(" ",""));
        sb.insert(Operator.A_SIZE," ");
        sb.insert(Operator.A_SIZE+Operator.B_SIZE+1," ");

        return sb.toString();
    }

    public static void printHex(char value){
        System.out.println(convertHex(value));
    }
    public static void printDec(char value){
        System.out.println(convertDec(value));
    }
    public static void printBin(char value){
        System.out.println(convertBin(value));
    }
    public static void printOp(char value){
        System.out.println(convertOp(value));
    }

    public static void printDump(char[] input){
        System.out.println(dump(input));
    }

    public static String dump(char[] input){
        StringBuilder sb = new StringBuilder();
        char counter = 0;
        for(int i=0;i<input.length;i++){
            if(i%8==0){
                sb.append("\n"+convertHex(counter)+":\t");
            }
            sb.append(" "+convertHex(input[i]));
            counter++;
        }


        for(int i=0;i<8-counter%8;i++){
            if(counter!=0)
                sb.append(" "+convertHex((char)0));
        }

        return sb.toString().substring(1);
    }
    
    public static String stripLiteral(String input){
        return input.substring(2);
    }
}
