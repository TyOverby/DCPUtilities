package com.prealpha.dcputil.defaults;

import com.prealpha.dcputil.compiler.defaults.BasicAssembler;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.emulator.HaltConditions;
import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.EmulatorException;

/**
 * User: Ty
 * Date: 7/19/12
 * Time: 7:46 PM
 */
public class BasicSystem {
    private final BasicAssembler assembler = new BasicAssembler();
    public final Machine machine = new Machine();

    public void load(String program) throws ParserException {
        char[] pro = assembler.assemble(program);
        machine.load(pro);
    }

    public void step() throws EmulatorException {
        machine.step();
    }
    public void run(boolean fireEvents, HaltConditions... conditions) throws EmulatorException {
        machine.run(fireEvents, conditions);
    }
}
