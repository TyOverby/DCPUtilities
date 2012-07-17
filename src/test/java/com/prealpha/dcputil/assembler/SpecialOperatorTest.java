package com.prealpha.dcputil.assembler;

import org.junit.Test;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 7:13 AM
 */
public class SpecialOperatorTest extends CompilerTest{
   @Test
   public void testSpecial(){
       {
           String input = "JSR A";
           char[] expected = {0x0020};
           assertEqual(expected,compile(input));
       }

       {
           String input = "INT A";
           char[] expected = {0x0100};
           assertEqual(expected,compile(input));
       }
   }
}
