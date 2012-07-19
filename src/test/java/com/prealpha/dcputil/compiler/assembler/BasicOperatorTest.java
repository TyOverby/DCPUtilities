package com.prealpha.dcputil.compiler.assembler;

import org.junit.Test;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 6:57 AM
 */
public class BasicOperatorTest extends CompilerTest
{
    @Test
    public void testMath(){

        {
            String input = "ADD A A";
            char[] expected = {0x0002};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SUB A A ";
            char[] expected = {0x0003};
            assertEqual(expected,compile(input));
        }

        {
            String input = "MUL A A";
            char[] expected = {0x0004};
            assertEqual(expected,compile(input));
        }

        {
            String input = "MLI A A";
            char[] expected = {0x0005};
            assertEqual(expected,compile(input));
        }

        {
            String input = "DIV A A";
            char[] expected = {0x0006};
            assertEqual(expected,compile(input));
        }

        {
            String input = "DVI A A";
            char[] expected = {0x0007};
            assertEqual(expected,compile(input));
        }

        {
            String input = "MOD A A";
            char[] expected = {0x0008};
            assertEqual(expected,compile(input));
        }

        {
            String input = "MDI A A";
            char[] expected = {0x0009};
            assertEqual(expected,compile(input));
        }
    }

    @Test
    public void testBitFiddling(){
        {
            String input = "AND A A";
            char[] expected = {0x000A};
            assertEqual(expected,compile(input));
        }

        {
            String input = "BOR A A";
            char[] expected = {0x000B};
            assertEqual(expected,compile(input));
        }

        {
            String input = "XOR A A";
            char[] expected = {0x000C};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SHR A A";
            char[] expected = {0x000D};
            assertEqual(expected,compile(input));
        }

        {
            String input = "ASR A A";
            char[] expected = {0x000E};
            assertEqual(expected,compile(input));
        }
    }

    @Test
    public void testNext(){
        {
            String input = "IFB A A";
            char[] expected = {0x0010};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFC A A";
            char[] expected = {0x0011};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFE A A";
            char[] expected = {0x0012};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFN A A";
            char[] expected = {0x0013};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFG A A";
            char[] expected = {0x0014};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFA A A";
            char[] expected = {0x0015};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFL A A";
            char[] expected = {0x0016};
            assertEqual(expected,compile(input));
        }

        {
            String input = "IFU A A";
            char[] expected = {0x0017};
            assertEqual(expected,compile(input));
        }
    }

    @Test
    public void testOther(){
        {
            String input = "ADX A A";
            char[] expected = {0x001a};
            assertEqual(expected,compile(input));
        }

        {
            String input = "SBX A A";
            char[] expected = {0x001b};
            assertEqual(expected,compile(input));
        }

        {
            String input = "STI A A";
            char[] expected = {0x001e};
            assertEqual(expected,compile(input));
        }
        {
            String input = "STD A A";
            char[] expected = {0x001f};
            assertEqual(expected,compile(input));
        }
    }
}
