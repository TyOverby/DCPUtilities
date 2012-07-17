package com.prealpha.dcputil.assembler;

import com.prealpha.dcputil.data.Pack;
import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 9:28 AM
 */
public class Builder {
    public static char makeInstruction(Pack opPack, Pack bPack, Pack aPack){
        char result = 0x0;
        char op = opPack.getCode();
        char a = aPack.getCode();
        char b = bPack.getCode();
        
        a <<=10;
        b <<=5;

        result += op;
        result += a;
        result += b;
        
        return result;
    }

    public static char makeSpecialInstruction(Operator.OperatorPack opPack, Value.ValuePack aPack){
        char result = 0x0;
        char op = opPack.getCode();
        char a = aPack.getCode();

        a <<=10;
        op <<=5;

        result += op;
        result += a;

        return result;
    }
}
