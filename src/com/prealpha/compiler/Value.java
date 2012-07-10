package com.prealpha.compiler;

import com.prealpha.data.Keeper;
import com.prealpha.data.Pack;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:39 AM
 */
public class Value {
    private static final Keeper values = new Keeper();

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

        add("[A+next]", 0x10);
        add("[B+next]", 0x11);
        add("[C+next]", 0x12);
        add("[X+next]", 0x13);
        add("[Y+next]", 0x14);
        add("[Z+next]", 0x15);
        add("[I+next]", 0x16);
        add("[J+next]", 0x17);
        
        add("PUSH/POP",     0x18);
        add("[SP]",         0x19);
        add("[SP+next]",    0x1a);
        add("SP",           0x1b);
        
        add("PC",       0x1c);
        add("EX",       0x1d);
        add("[next]",   0x1f);

        // A can be a literal in-between 0 and 30
        for(int i=0;i<=30;i++){
            char code = (char)(i + 0x20);
            add(""+i,code,i);
        }
    }

    private static void add(String ident, char code){
        values.add(ident,code, Pack.Type.VALUE);
    }
    private static void add(String ident, int code){
        add(ident,(char)code);
    }


    private static void add(String ident, char code, char value){
        values.add(new Pack(ident,code, value));
    }
    private static void add(String ident, int code, int value){
        add(ident,(char) code, (char) value);
    }
}
