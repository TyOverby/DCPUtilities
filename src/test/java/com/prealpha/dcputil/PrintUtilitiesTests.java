package com.prealpha.dcputil;

import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.*;
import static junit.framework.Assert.assertEquals;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 3:53 AM
 */
public class PrintUtilitiesTests {
    @Test
    public void convertHexTest(){
        char c = 10;
        assertEquals(convertHex(c),"0x000a");
        assertEquals(stripLiteral(convertHex(c)),"000a");

        c = 65535;
        assertEquals(convertHex(c),"0xffff");
        assertEquals(stripLiteral(convertHex(c)),"ffff");
        
    }

    @Test
    public void convertDecTest(){
        char c = 0x1000;
        printDec(c);
    }

    public static void print(Object o){
        System.out.println(o.toString());
    }
}
