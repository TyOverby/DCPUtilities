package com.prealpha.dcputil;

import com.prealpha.dcputil.compiler.Builder;
import com.prealpha.dcputil.data.Pack;
import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;
import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.*;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 5:22 AM
 */
public class Playground {
    //@Test
    public  void test1(){
        char c = 10;

        printDec(c);
        printHex(c);
        printBin(c);
        printOp(c);
    }

    //@Test
    public void test2(){
        char a = 63;
        printOp(a);

        a <<=4+6;
        printOp(a);
        System.out.println();


        char b = 63;
        printOp(b);

        b <<=4;
        printOp(b);
        System.out.println();


        char c = 15;
        printOp(c);
        System.out.println();

        char d = a;
        d+=b;
        d+=c;
        printDec(d);
        printHex(d);
        printOp(d);
    }

    //@Test
    public void test3(){
        // SET PC, PC
        {
            Pack op = getOp("SUB");
            Pack b  = getVal("X");
            Pack a  = getVal("0");

            char expected = 0x8463;
            char result = Builder.makeInstruction(op, b, a);

            printHex(((Value.ValuePack)getVal("-1")).getValue());
//            System.out.println("[expected]");
//            printHex(expected);
//            printOp(expected);
//
//            System.out.println("\n[actual]");
//            printHex(result);
//            printOp(result);
        }
    }




    private static Pack getOp(String id){
        return Operator.operators.get(id);
    }
    private static Pack getVal(String id){
        return Value.values.get(id);
    }
}
