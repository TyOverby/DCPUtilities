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
    private static final Keeper values = new Keeper();
    static{
        add("SET", 0x01);

        add("ADD", 0x02);
        add("SUB", 0x03);
        add("MUL", 0x04);
        add("MLI", 0x05);
        add("DIV", 0x06);
        add("DVI", 0x07);
        add("MOD", 0x08);
        add("AND", 0x09);
        add("BOR", 0x0a);
        add("XOR", 0x0b);
        add("SHR", 0x0c);
        add("ASR", 0x0d);
        add("SHL", 0x0e);

        add("IFB", 0x10);
        add("IFC", 0x11);
        add("IFE", 0x12);
        add("IFN", 0x13);
        add("IFG", 0x14);
        add("IFA", 0x15);
        add("IFL", 0x15);
        add("IFU", 0x15);
        
        
        // Specials
        addSpecial("JSR", 0x01);

        addSpecial("INT",0x08);
        addSpecial("ING",0x09);
        addSpecial("INS",0x0a);

        addSpecial("HWN",0x10);
        addSpecial("HWQ",0x11);
        addSpecial("HWI",0x12);
    }

    private static void add(String ident, char code){
        values.add(ident, code, Pack.Type.VALUE);
    }
    private static void add(String ident, int code){
        add(ident,(char)code);
    }

    private static void addSpecial(String ident, char code){
        values.add(ident, code, Pack.Type.VALUE);
    }
    private static void addSpecial(String ident, int code){
        add(ident,(char)code);
    }
}
