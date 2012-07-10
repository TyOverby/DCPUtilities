import com.prealpha.compiler.Operator;
import com.prealpha.compiler.Operator.Op;
import static com.prealpha.compiler.Operator.Op.*;
import org.junit.Test;

import static com.prealpha.util.PrintUtilities.*;

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
            char op = 0x01; // SET
            char a = 0x1c;  // PC
            char b = 0x18;  // POP

            char expected = 0x6381;
            char result = Operator.makeInstruction(op,a,b);

            printHex(expected);
            printOp(expected);

            printHex(result);
            printOp(result);
        }
    }
}
