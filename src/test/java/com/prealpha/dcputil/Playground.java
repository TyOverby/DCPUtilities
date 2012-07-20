package com.prealpha.dcputil;

import com.prealpha.dcputil.compiler.assembler.CompilerTest;
import com.prealpha.dcputil.compiler.lexer.Expression;
import com.prealpha.dcputil.compiler.lexer.Lexer;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.Machine;
import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.*;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 5:22 AM
 */
public class Playground extends CompilerTest{

    public static  void main(String... args) throws EmulatorException {
        Machine machine = new Machine();
        char[] program = new char[]{0x8802,0xac14, 0x7f81, 0x0006, 0x7f81};
        machine.load(program);
        machine.runUntilOverflow();
        System.out.println((int)machine.getRegisters()[0]);
    }
}
