package com.prealpha.dcputil;

import com.prealpha.dcputil.compiler.assembler.CompilerTest;
import com.prealpha.dcputil.compiler.lexer.Expression;
import com.prealpha.dcputil.compiler.lexer.Lexer;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.defaults.BasicSystem;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.StepEvent;
import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.*;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 5:22 AM
 */
public class Playground extends CompilerTest{

    public static  void main(String... args) throws EmulatorException, ParserException {
        final BasicSystem bs = new BasicSystem();
        bs.load(":loop | ADD A 1 | SET PC loop");
        bs.machine.addStepEvent(new StepEvent() {
            private int count = 0;
            @Override
            public void onStep() {
                System.out.println((int)bs.machine.getPc()+": "+count++ +"->"+(int)bs.machine.getRegisters()[0]);
            }
        });

        bs.run();
    }
}
