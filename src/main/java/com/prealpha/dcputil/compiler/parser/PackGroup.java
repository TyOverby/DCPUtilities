package com.prealpha.dcputil.compiler.parser;

import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 8:56 AM
 */
public class PackGroup {
    public final Operator.OperatorPack operator;
    public final Value.ValuePack[] values;

    public PackGroup(Operator.OperatorPack operator, Value.ValuePack... values){
        this.operator = operator;
        this.values = values;
    }

}
