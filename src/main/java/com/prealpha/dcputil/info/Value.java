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
        private final String identifier;
        private final char code;
        private final int cycles;
        private final char value;
        
        public ValuePack(String identifier, char code, int cycles, char value){
            this.identifier = identifier;
            this.code = code;
            this.cycles = cycles;
            this.value = value;
        }
        public ValuePack(String identifier, char code, int cycles){
            this(identifier, code, cycles, (char) 0);
        }
        public ValuePack(String identifier, char code, char value){
            this(identifier,code,0,value);
        }
        public ValuePack(String identifier, char code){
            this(identifier, code, 0, (char) 0);
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

        public char getValue(){
            return this.value;
        }
    }
    public static final Keeper<ValuePack> values = new Keeper<ValuePack>();

    static{
        add("A", 0x00);
        add("B", 0x01);
        add("C", 0x02);
        add("X", 0x03);
        add("Y", 0x04);
        add("Z", 0x05);
        add("I", 0x06);
        add("J", 0x07);

        add("[A]", 0x08);
        add("[B]", 0x09);
        add("[C]", 0x0a);
        add("[X]", 0x0b);
        add("[Y]", 0x0c);
        add("[Z]", 0x0d);
        add("[I]", 0x0e);
        add("[J]", 0x0f);

        add("[A+next]", 0x10, 1);
        add("[B+next]", 0x11, 1);
        add("[C+next]", 0x12, 1);
        add("[X+next]", 0x13, 1);
        add("[Y+next]", 0x14, 1);
        add("[Z+next]", 0x15, 1);
        add("[I+next]", 0x16, 1);
        add("[J+next]", 0x17, 1);
        
        add("PUSH/POP",     0x18);
        add("[SP]",         0x19);
        add("[SP+next]",    0x1a);
        add("SP",           0x1b);
        
        add("PC",       0x1c);
        add("EX",       0x1d);
        add("[next]",   0x1e, 1);
        add("next",     0x1f, 1);

        // A can be a literal in-between 0 and 30
        for(int i=0;i<=30;i++){
            char code = (char)(i + 0x20);
            add(""+i,code,i);
        }
    }

    private static void add(String identifier, int code, int clock){
        values.add(new ValuePack(identifier, (char) code, clock));
    }
    private static void add(String identifier, int code){
        values.add(new ValuePack(identifier, (char) code));
    }
}
