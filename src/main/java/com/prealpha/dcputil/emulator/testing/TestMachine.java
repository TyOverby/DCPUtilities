package com.prealpha.dcputil.emulator.testing;

import com.prealpha.dcputil.compiler.defaults.BasicAssembler;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.EmulatorException;

/**
 * User: Ty
 * Date: 7/26/12
 * Time: 5:53 AM
 */
public class TestMachine extends Machine {
    private final BasicAssembler assembler = new BasicAssembler();

    public void load(String program) throws ParserException {
        char[] p = assembler.assemble(program);
        this.load(p);
    }

    public void test() throws EmulatorException, EndOfMemoryException {
        // this.isRunning can be modified on the inside by BRK
        this.isRunning = true;
        while(this.isRunning){
            this.step();

            if(this.pc >= this.sp && this.sp > 0) {
                this.isRunning = false;
                throw new EndOfMemoryException();
            }
        }
    }
}
