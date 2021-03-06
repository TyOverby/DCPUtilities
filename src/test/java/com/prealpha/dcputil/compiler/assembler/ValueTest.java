package com.prealpha.dcputil.compiler.assembler;

import org.junit.Test;

/**
 * User: Ty
 * Date: 7/18/12
 * Time: 3:33 AM
 */
public class ValueTest extends CompilerTest{
    @Test
    public void testARegisters(){
        {
            String input = "SET A A";
            char[] expected = {0x0001};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A B";
            char[] expected = {0x0401};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A C";
            char[] expected = {0x0801};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A X";
            char[] expected = {0x0c01};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A Y";
            char[] expected = {0x1001};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A Z";
            char[] expected = {0x1401};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A I";
            char[] expected = {0x1801};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A J";
            char[] expected = {0x1c01};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A PC";
            char[] expected = {0x7001};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A SP";
            char[] expected = {0x6c01};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET A EX";
            char[] expected = {0x7401};
            assertEqual(expected,compile(input));
        }
    }

    @Test
    public void testBRegisters(){
        {
            String input = "SET A A";
            char[] expected = {0x0001};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET B A";
            char[] expected = {0x0021};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET C A";
            char[] expected = {0x0041};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET X A";
            char[] expected = {0x0061};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET Y A";
            char[] expected = {0x0081};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET Z A";
            char[] expected = {0x00A1};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET I A";
            char[] expected = {0x00C1};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET J A";
            char[] expected = {0x00E1};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET PC A";
            char[] expected = {0x0381};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET SP A";
            char[] expected = {0x0361};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SET EX A";
            char[] expected = {0x03A1};
            assertEqual(expected,compile(input));
        }
    }

    @Test
    public void testALiterals(){
        {
            String input = "SET A 0xffff";
            char[] expected = {0x8001};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET A 0";
            char[] expected = {0x8401};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET A 1";
            char[] expected = {0x8801};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET A 10";
            char[] expected = {0xac01};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET A 30";
            char[] expected = {0xfc01};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET A 31";
            char[] expected = {0x7c01,0x001f};
            char[] actual   = compile(input);

            assertEqual(expected,actual);
        }
    }

    @Test
    public void testARegisterPointer(){
        {
            String input = "ADD A [A]";
            char[] expected = {0x2002};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [B]";
            char[] expected = {0x2402};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [C]";
            char[] expected = {0x2802};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [X]";
            char[] expected = {0x2c02};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [Y]";
            char[] expected = {0x3002};
            assertEqual(expected, compile(input));
        }

        {
            String input = "ADD A [Z]";
            char[] expected = {0x3402};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [I]";
            char[] expected = {0x3802};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [J]";
            char[] expected = {0x3c02};
            //char[] actual   = compile(input);

            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [J]";
            char[] expected = {0x3c02};

            assertEqual(expected,compile(input));
        }

        {
            String input = "ADD A [SP]";
            char[] expected = {0x6402};

            assertEqual(expected, compile(input));
        }
    }

    @Test
    public void testALiteralPointer(){
        {
            String input = "SET A [0]";
            char[] expected = {0x7801,0x0000};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET A [560]";
            char[] expected = {0x7801,0x0230};
            assertEqual(expected,compile(input));
        }

    }
    @Test
    public void testBLiteralPointer(){
        {
            String input = "SET [0] 550";
            char[] expected = {0x7fc1,0x0226,0x0000};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET [550] 550";
            char[] expected = {0x7fc1,0x0226,0x0226};
            assertEqual(expected,compile(input));
        }

    }

    @Test
    public void testABLiteralPointer(){
        {
            String input = "SET [0] [550]";
            char[] expected = {0x7bc1,0x0226,0x0000};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET [550] [550]";
            char[] expected = {0x7bc1,0x0226,0x0226};
            assertEqual(expected,compile(input));
        }
    }

    @Test
    public void testStackOperations(){
        {
            String input = "SET [550] POP";
            char[] expected = {0x63c1,0x0226};
            assertEqual(expected,compile(input));
        }
        {
            String input = "SET X POP";
            char[] expected = {0x6061};
            assertEqual(expected,compile(input));
        }
    }

}
