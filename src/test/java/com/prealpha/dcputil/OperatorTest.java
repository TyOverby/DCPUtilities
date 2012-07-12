package com.prealpha.dcputil;

import com.prealpha.dcputil.compiler.Builder;
import com.prealpha.dcputil.data.Pack;
import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class OperatorTest{
    @Test
    public void makeInstructionTest(){
        // SET PC, PC
        {
            Pack op = getOp("SET");
            Pack b  = getVal("PC");
            Pack a  = getVal("PC");

            char expected = 0x7381;
            char result = Builder.makeInstruction(op, b, a);
            assertEquals(expected, result);
        }

        {
            Pack op = getOp("SET");
            Pack b  = getVal("PC");
            Pack a  = getVal("PUSH/POP");

            char expected = 0x6381;
            char result = Builder.makeInstruction(op,b,a);
            assertEquals(expected, result);
        }

        {
            Pack op = getOp("SUB");
            Pack b  = getVal("X");
            Pack a  = getVal("0");

            char expected = 0xac63;
            char result = Builder.makeInstruction(op, b, a);
            assertEquals(expected, result);
        }
    }

    private static Pack getOp(String id){
        return Operator.operators.get(id);
    }
    private static Pack getVal(String id){
        return Value.values.get(id);
    }
}