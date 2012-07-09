package com.prealpha.compiler;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 4:54 AM
 */
public class Operator {
    public static enum Op{
        SET,
        ADD,
        SUB,
    }
    private static Map<Op, Character> opMap;

    static{
        opMap = new HashMap<>();
        opMap.put(Op.SET,(char) 0x1);
        opMap.put(Op.ADD,(char) 0x2);
        opMap.put(Op.SUB,(char) 0x3);
    }

    public static char getBits(Op op){
        return opMap.get(op);
    }

    public static Map<Op, Character> getOpMap(){
        return opMap;
    }
}
