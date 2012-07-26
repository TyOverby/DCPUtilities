package com.prealpha.dcputil.compiler.parser;

import com.prealpha.dcputil.compiler.info.Operator;
import com.prealpha.dcputil.compiler.info.Value;

import java.util.List;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 8:56 AM
 */
public class PackGroup {
    public final Operator.OperatorPack operator;
    public final List<Value.ValuePack> values;

    public PackGroup(Operator.OperatorPack operator, List<Value.ValuePack> values){
        this.operator = operator;
        this.values = values;
    }

}
