package com.prealpha.compiler;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 9:28 AM
 */
public class Builder {
    public static char makeInstruction(char operation, char b, char a){
        char result = 0x0;


        a <<= 10;
        b <<= 5;

        result += operation;
        result += a;
        result += b;

        return result;
    }
}
