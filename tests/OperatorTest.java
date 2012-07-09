import com.prealpha.compiler.Operator;
import org.junit.Test;

import static com.prealpha.util.PrintUtilities.printOp;

public class OperatorTest{
    @Test
    public void operatorSetTest(){

    }

    @Test
    public void makeInstructionTest(){
        char op = 0xf;
        char a  = 0x00;
        char b  = 0x00;

        char res = Operator.makeInstruction(op,a,b);

        printOp(res);
    }
}