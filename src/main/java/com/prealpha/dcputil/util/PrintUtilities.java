package com.prealpha.dcputil.util;

import com.prealpha.dcputil.compiler.info.Operator;

import java.util.*;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 3:48 AM
 */
public class PrintUtilities {
    public static String convertHex(char value){
        final int MAX_LENGTH = 4;
        String converted = Integer.toHexString(value);
        while(converted.length()<MAX_LENGTH){
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

    private static String convertBin(int value) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(value));
        while(sb.length()<32){
            sb.insert(0,"0");
        }
        int i=0;
        sb.insert(8+i++,"\n");
        sb.insert(16+i++,"\n");
        sb.insert(24+i,"\n");
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
    public static void printBin(int value){
        System.out.print(convertBin(value));
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
                sb.append("\n").append(convertHex(counter)).append(":\t");
            }
            sb.append(" ").append(convertHex(input[i]));
            counter++;
        }


        for(int i=0;i<8-counter%8;i++){
            if(counter!=0)
                sb.append(" ").append(convertHex((char) 0));
        }

        return sb.toString().substring(1);
    }
    public static String dump(char[] input, int offset, int... highlight){
        StringBuilder sb = new StringBuilder();
        char counter = 0;
        for(int i=0;i<input.length;i++){
            String space = " ";
            for(int h:highlight){
                if(i==h){
                    space = "#";
                }
            }

            if(i%8==0){
                sb.append("\n").append(convertHex((char) (counter + offset))).append(":\t");
            }
            sb.append(space).append(convertHex(input[i])).append(space);
            counter++;
        }


        for(int i=0;i<8-counter%8;i++){
            if(counter!=0)
                sb.append(" ").append(convertHex((char) 0));
        }

        return sb.toString().substring(1);
    }
    public static String dump(char[] input,int... highlight){
        return dump(input, 0, highlight);
    }
    
    public static String stripLiteral(String input){
        return input.substring(2);
    }



    public static String dump(char[] input, boolean[] mask, Set<Character> visitedSet,int...highlight){
        List<Character> visitedList = new ArrayList<Character>(visitedSet);
        Collections.sort(visitedList);
        Set<Integer> alreadyPrinted  = new HashSet<Integer>();
        StringBuilder toReturn = new StringBuilder();
        int lastIndex =0;

        for(int i:visitedList){
            if(!alreadyPrinted.contains(i)){
                char offset = (char) (i%8);
                char index = (char) (i-offset);
                if(index-lastIndex>8){
                    toReturn.append("\n");
                }
                lastIndex = index;
                toReturn.append(convertHex(index));
                toReturn.append(":  ");
                for(int k = i-offset;k<(i-offset+8);k++){

                    char h = ' ';
                    for(int g:highlight){
                        if(k==g){
                            h = '#';
                            break;
                        }
                    }

                    alreadyPrinted.add(k);
                    toReturn.append(h);
                    toReturn.append(convertHex(input[k]));
                    toReturn.append(h);
                }
                toReturn.append("\n");
            }
        }

        return toReturn.toString();
    }
}
