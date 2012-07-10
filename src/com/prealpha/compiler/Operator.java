package com.prealpha.compiler;
import com.prealpha.data.Keeper;
import com.prealpha.data.Pack;
import com.sun.org.apache.bcel.internal.generic.JSR;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 4:54 AM
 */
public class Operator {
    private static class OperatorPack extends Pack{
        private final String identifier;
        private final char code;
        private final int cycles;

        private final boolean isSpecial;

        public OperatorPack(String identifier, char code, int cycles, boolean isSpecial){
            this.identifier = identifier;
            this.code = code;
            this.cycles = cycles;
            this.isSpecial = isSpecial;
        }

        @Override
        public String getIdentifier() {
            return this.identifier;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public char getCode() {
            return this.code;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int getCycles() {
            return this.cycles;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
    private static final Keeper values = new Keeper();
    static{
        add("SET", 0x01, 1);

        add("ADD", 0x02, 2);
        add("SUB", 0x03, 2);
        add("MUL", 0x04, 2);
        add("MLI", 0x05, 2);
        add("DIV", 0x06, 3);
        add("DVI", 0x07, 3);
        add("MOD", 0x08, 3);
        add("AND", 0x09, 1);
        add("BOR", 0x0a, 1);
        add("XOR", 0x0b, 1);
        add("SHR", 0x0c, 2);
        add("ASR", 0x0d, 2);
        add("SHL", 0x0e, 2);

        add("IFB", 0x10, 2);
        add("IFC", 0x11, 2);
        add("IFE", 0x12, 2);
        add("IFN", 0x13, 2);
        add("IFG", 0x14, 2);
        add("IFA", 0x15, 2);
        add("IFL", 0x15, 2);
        add("IFU", 0x15, 2);
        
        
        // Specials
        addSpecial("JSR", 0x01, 3);

        addSpecial("INT",0x08, 4);
        addSpecial("ING",0x09, 1);
        addSpecial("INS",0x0a, 1);

        addSpecial("HWN",0x10, 2);
        addSpecial("HWQ",0x11, 4);
        addSpecial("HWI",0x12, 4);
    }

    private static void add(String ident, int code, int cycles){
        values.add(new OperatorPack(ident, (char) code, cycles, false));
    }

    private static void addSpecial(String ident, int code, int cycles){
        values.add(new OperatorPack(ident, (char) code, cycles, true));
    }
}
