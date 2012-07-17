package com.prealpha.dcputil.info;

import com.prealpha.dcputil.data.Keeper;
import com.prealpha.dcputil.data.Pack;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 4:54 AM
 */
public class Operator {
    public static final int TOTAL_SIZE = 16;
    public static final int OP_SIZE = 5;
    public static final int A_SIZE = 6;
    public static final int B_SIZE = 5;
    public static class OperatorPack extends Pack {
        private final String[] identifiers;
        private final char code;
        private final int cycles;

        private final boolean isSpecial;

        public OperatorPack(char code, int cycles, boolean isSpecial, String... identifiers){
            this.identifiers = identifiers;
            this.code = code;
            this.cycles = cycles;
            this.isSpecial = isSpecial;
        }

        @Override
        public String[] getIdentifiers() {
            return this.identifiers;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public char getCode() {
            return this.code;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int getCycles() {
            return this.cycles;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public OperatorPack clone() {
            return new OperatorPack(code,cycles,isSpecial,identifiers);  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public static final Keeper<OperatorPack> operators = new Keeper<OperatorPack>();

    static{
        add("DAT", 0xffff, 0);
        add("SET", 0x01, 1);

        add("ADD", 0x02, 2);
        add("SUB", 0x03, 2);
        add("MUL", 0x04, 2);
        add("MLI", 0x05, 2);
        add("DIV", 0x06, 3);
        add("DVI", 0x07, 3);
        add("MOD", 0x08, 3);
        add("MDI", 0x09, 3);
        add("AND", 0x0a, 1);
        add("BOR", 0x0b, 1);
        add("XOR", 0x0c, 1);
        add("SHR", 0x0d, 2);
        add("ASR", 0x0e, 2);
        add("SHL", 0x0f, 2);

        add("IFB", 0x10, 2);
        add("IFC", 0x11, 2);
        add("IFE", 0x12, 2);
        add("IFN", 0x13, 2);
        add("IFG", 0x14, 2);
        add("IFA", 0x15, 2);
        add("IFL", 0x16, 2);
        add("IFU", 0x17, 2);

        add("ADX", 0x1a, 3);
        add("SBX", 0x1b, 3);

        add("STI", 0x1e, 2);
        add("STD", 0x1f, 2);
        
        
        // Specials
        addSpecial("JSR", 0x01, 3);

        addSpecial("INT", 0x08, 4);
        addSpecial("IAG", 0x09, 1);
        addSpecial("IAS", 0x0a, 1);
        addSpecial("RFI", 0x0b, 1);
        addSpecial("IAQ", 0x0c, 1);

        addSpecial("HWN", 0x10, 2);
        addSpecial("HWQ", 0x11, 4);
        addSpecial("HWI", 0x12, 4);
        operators.seal();
    }

    private static void add(String ident, int code, int cycles){
        operators.add(new OperatorPack((char) code, cycles, false, ident));
    }

    private static void addSpecial(String ident, int code, int cycles){
        operators.add(new OperatorPack((char) code, cycles, true, ident));
    }
}
