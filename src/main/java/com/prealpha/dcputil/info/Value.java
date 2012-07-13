package com.prealpha.dcputil.info;


import com.prealpha.dcputil.data.Keeper;
import com.prealpha.dcputil.data.Pack;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:39 AM
 */
public class Value {
    public static class ValuePack extends Pack {
        private final String[] identifiers;
        private final char code;
        private final int cycles;
        private final char value;
        
        public ValuePack(char code, int cycles, char value, String... identifiers){
            this.identifiers = identifiers;
            this.code = code;
            this.cycles = cycles;
            this.value = value;
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

        public char getValue(){
            return this.value;
        }
    }
    public static final Keeper<ValuePack> values = new Keeper<ValuePack>();

    static{
        add(0x00, "A");
        add(0x01, "B");
        add(0x02, "C");
        add(0x03, "X");
        add(0x04, "Y");
        add(0x05, "Z");
        add(0x06, "I");
        add(0x07, "J");

        add(0x08, "[A]");
        add(0x09, "[B]");
        add(0x0a, "[C]");
        add(0x0b, "[X]");
        add(0x0c, "[Y]");
        add(0x0d, "[Z]");
        add(0x0e, "[I]");
        add(0x0f, "[J]");

        add(0x10, 1, "[A+next]");
        add(0x11, 1, "[B+next]");
        add(0x12, 1, "[C+next]");
        add(0x13, 1, "[X+next]");
        add(0x14, 1, "[Y+next]");
        add(0x15, 1, "[Z+next]");
        add(0x16, 1, "[I+next]");
        add(0x17, 1, "[J+next]");
        
        add(0x18, "PUSH/POP", "PUSH", "POP");
        add(0x19, "PEEK");
        add(0x19, "[SP]", "PEEK");
        add(0x1a, "[SP+next]");
        add(0x1b, "SP");
        
        add(0x1c, "PC");
        add(0x1d, "EX");
        add(0x1e, 1, "[next]");
        add(0x1f, 1, "next");

        // A can be a literal in-between 0 and 30
        for(int i=-1;i<=30;i++){
            char code = (char)(i + 0x21);
            int k = i;
            if(i==-1){
                add(code, (char)i, ""+k);
                k = 0xffff;
            }
            add(code, (char)i, ""+k);
        }

        values.seal();
    }

    private static void add(int code, int clock, String... identifiers){
        values.add(new ValuePack((char) code, clock, (char) 0, identifiers));
    }
    private static void add(int code, String... identifiers){
        values.add(new ValuePack((char) code, 0 , (char) 0 , identifiers));
    }
    private static void add(int code, char data, String... identifiers){
        values.add(new ValuePack((char) code, 0, data, identifiers));
    }
}
