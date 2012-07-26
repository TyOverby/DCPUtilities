package com.prealpha.dcputil.emulator.testing;

import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.testing.EndOfMemoryException;
import com.prealpha.dcputil.emulator.testing.TestMachine;

import java.util.List;

/**
 * User: Ty
 * Date: 7/26/12
 * Time: 6:13 AM
 */


abstract public class BasicMachineTest {
    private TestMachine tm = new TestMachine();

    public void test(String program) throws ParserException, EmulatorException, EndOfMemoryException {
        tm.load(program);
        tm.test();
    }
    public void test(List<String> program) throws EmulatorException, EndOfMemoryException, ParserException {
        StringBuilder sb = new StringBuilder();
        for(String s: program){
            sb.append(s);
            sb.append("\n");
        }
        test(sb.toString());
    }
    public char[] getReg(){
        return tm.getRegisters();
    }
    public char getReg(int reg){
        return tm.getRegisters()[reg];
    }
    public char[] getMem(){
        return tm.getMemory();
    }
}
