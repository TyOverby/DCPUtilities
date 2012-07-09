package com.prealpha.util;

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

    public static void printDec(char value){
        System.out.println(convertDec(value));
    }
    public static void printHex(char value){
        System.out.println(convertHex(value));
    }
    
    public static String stripLiteral(String input){
        return input.substring(2);
    }
}
