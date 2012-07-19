package com.prealpha.dcputil.compiler.assembler;

import com.prealpha.dcputil.compiler.parser.PackGroup;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.info.Value.ValuePack;
import com.prealpha.dcputil.compiler.lexer.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:41 AM
 */
public class Assembler {
    private List<Expression> expressionList;
    private List<Character> charList = new ArrayList<Character>();

    public char[] assemble(List<PackGroup> packGroups) throws ParserException {
        List<Character> toReturn = new ArrayList<Character>();
        for(PackGroup pg:packGroups){
            toReturn.addAll(assembleExpression(pg));
        }

        char[] buffer = new char[toReturn.size()];
        for(int i=0;i<buffer.length;i++){
            buffer[i]=toReturn.get(i);
        }
        return buffer;
    }

    public List<Character> assembleExpression(PackGroup pg){
        List<Character> toReturn = new ArrayList<Character>();
        if(!pg.operator.is("DAT")){
            switch(pg.values.length){
                case 2:
                    toReturn.add(Builder.makeInstruction(pg.operator,pg.values[0],pg.values[1]));
                    if(pg.values[1].getData()!=null){
                        toReturn.add(pg.values[1].getData());
                    }
                    if(pg.values[0].getData()!=null){
                        toReturn.add(pg.values[0].getData());
                    }
                    break;
                case 1:
                    toReturn.add(Builder.makeSpecialInstruction(pg.operator,pg.values[0]));
                    if(pg.values[0].getData()!=null){
                        toReturn.add(pg.values[0].getData());
                    }
                    break;
                default:
                    System.err.println(pg.values.length);
            }
        }
        else{
            for(ValuePack vp:pg.values){
                toReturn.add(vp.getData());
            }
        }

        return toReturn;
    }
}
