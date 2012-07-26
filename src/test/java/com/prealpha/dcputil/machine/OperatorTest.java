package com.prealpha.dcputil.machine;

import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.testing.EndOfMemoryException;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: Ty
 * Date: 7/26/12
 * Time: 6:21 AM
 */
public class OperatorTest extends BasicMachineTest {
    @Test
    public void basicTest() throws EmulatorException, EndOfMemoryException, ParserException {
        {
            test("SET A 5 | BRK");
            assertEquals(5,getReg(0));
        }
        {
            String input =  ":loop            \n" +
                            "  ADD A 1        \n" +
                            "  IFG A 5        \n" +
                            "    SET PC end   \n" +
                            "  SET PC loop    \n" +
                            ":end             \n" +
                            "BRK              \n";
            test(input);
            assertEquals(6, getReg(0));
        }
        {
            String input = "";
        }
    }
}
