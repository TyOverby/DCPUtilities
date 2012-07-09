package com.prealpha.compiler;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 4:54 AM
 */
public class Operator {
    public static final int TOTAL_SIZE = 16;
    public static final int OP_SIZE    = 5;
    public static final int A_SIZE     = 6;
    public static final int B_SIZE     = 5;

    public static enum Op{
        SET,

        ADD, SUB, MUL, MLI,
        DIV, DVI, MOD, AND,
        BOR, XOR, SHR, ASR,
        SHL,

        IFB, IFC, IFE, IFN,
        IFG, IFA, IFL, IFU
    }
    private static Map<Op, Character> opMap;

    static{
        opMap = new HashMap<>();
        opMap.put(Op.SET,(char) 0x01);

        opMap.put(Op.ADD,(char) 0x02);
        opMap.put(Op.SUB,(char) 0x03);
        opMap.put(Op.MUL,(char) 0x04);
        opMap.put(Op.MLI,(char) 0x05);
        opMap.put(Op.DIV,(char) 0x06);
        opMap.put(Op.DVI,(char) 0x07);
        opMap.put(Op.MOD,(char) 0x08);
        opMap.put(Op.AND,(char) 0x09);
        opMap.put(Op.BOR,(char) 0x0a);
        opMap.put(Op.XOR,(char) 0x0b);
        opMap.put(Op.SHR,(char) 0x0c);
        opMap.put(Op.ASR,(char) 0x0d);
        opMap.put(Op.SHL,(char) 0x0e);

        opMap.put(Op.IFB,(char) 0x10);
        opMap.put(Op.IFC,(char) 0x11);
        opMap.put(Op.IFE,(char) 0x12);
        opMap.put(Op.IFN,(char) 0x13);
        opMap.put(Op.IFG,(char) 0x14);
        opMap.put(Op.IFA,(char) 0x15);
        opMap.put(Op.IFL,(char) 0x15);
        opMap.put(Op.IFU,(char) 0x15);
    }

    public static char makeInstruction(char operation, char a, char b){
        char result = operation;
        a <<= (TOTAL_SIZE-A_SIZE);
        b <<= (OP_SIZE);
        result += a;
        result += b;

        return result;
    }

    public static boolean hasOperator(String input){
        for(Op op:opMap.keySet()){
            return (op.toString().toLowerCase().equals(input.toLowerCase()));
        }
        return false;
    }
    
    public static char getOpCode(Op op){
        return opMap.get(op);
    }
    public static Character getOpCode(String opName){
        for(Op op:opMap.keySet()){
            if(op.toString().toLowerCase().equals(opName.toLowerCase())){
                return opMap.get(op);
            }
        }
        return null;
    }
    
    public static Map<Op, Character> getOpMap(){
        return opMap;
    }
}
