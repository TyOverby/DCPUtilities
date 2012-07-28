package com.prealpha.dcputil.compiler.assembler;

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

       {
           String input = "IAG A";
           char[] expected = {0x0120};
           assertEqual(expected,compile(input));
       }

       {
           String input = "IAS A";
           char[] expected = {0x0140};
           assertEqual(expected,compile(input));
       }

       {
           String input = "RFI A";
           char[] expected = {0x0160};
           assertEqual(expected,compile(input));
       }

       {
           String input = "IAQ A";
           char[] expected = {0x0180};
           assertEqual(expected,compile(input));
       }

       {
           String input = "HWN A";
           char[] expected = {0x0200};
           assertEqual(expected,compile(input));
       }

       {
           String input = "HWQ A";
           char[] expected = {0x0220};
           assertEqual(expected,compile(input));
       }

       {
           String input = "HWI A";
           char[] expected = {0x0240};
           assertEqual(expected,compile(input));
       }
       {
           String input = "HWI 0";
           char[] expected = {0x8640};
           assertEqual(expected,compile(input));
       }
   }
}
