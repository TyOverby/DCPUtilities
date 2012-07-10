import com.prealpha.compiler.Operator;
import com.prealpha.compiler.Operator.Op;
import static com.prealpha.compiler.Operator.Op.*;
import org.junit.Test;

import static com.prealpha.util.PrintUtilities.printOp;
import static junit.framework.Assert.assertEquals;

public class OperatorTest{
    @Test
    public void makeInstructionTest(){
        // SET PC, PC
        {
            char op = 0x01; // SET
            char b = 0x1c;  // PC
            char a = 0x1c;  // PC

            char expected = 0x7381;
            char result = Operator.makeInstruction(op,b,a);
            assertEquals(expected, result);
        }

        // SET PC, POP
        {
            char op = 0x01; // SET
            char b = 0x1c;  // PC
            char a = 0x18;  // POP



            char expected = 0x6381;
            char result = Operator.makeInstruction(op,b,a);
            assertEquals(expected, result);
        }
        
//        // HWN A
//        {
//            Op op = HWN;
//            char a = 0x2; // A
//            char b = 0x0; // Leave blank
//
//            char expected = 0x0a00;
//            char result = Operator.makeInstruction(op,a,b);
//            assertEquals(expected, result);
//        }
    }
}