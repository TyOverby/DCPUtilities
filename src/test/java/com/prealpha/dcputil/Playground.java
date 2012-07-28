package com.prealpha.dcputil;

import com.prealpha.dcputil.compiler.assembler.CompilerTest;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.defaults.BasicSystem;
import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.StepEvent;

import static com.prealpha.dcputil.util.PrintUtilities.dump;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 5:22 AM
 */
public class Playground extends CompilerTest{
    public void test(){
        {
            String input = "HWI 0";
            char[] expected = {8640};
            System.out.println(dump(compile(input)));
            //assertEqual(expected,compile(input));
        }
    }

    public static  void main(String... args) throws EmulatorException, ParserException {
        new Playground().test();
    }
}
