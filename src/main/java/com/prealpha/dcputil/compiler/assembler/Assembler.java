package com.prealpha.dcputil.compiler.assembler;

import com.prealpha.dcputil.compiler.parser.PackGroup;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.compiler.info.Value.ValuePack;
import com.prealpha.dcputil.compiler.lexer.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:41 AM
 */
public class Assembler {
    private List<Expression> expressionList;
    private List<Character> charList = new ArrayList<Character>();

    private Map<Integer,Integer> locationToLine = new HashMap<Integer,Integer>();

    public int getLine(int location){
        if(locationToLine.containsKey(location)){
            return locationToLine.get(location);
        }
        if(location==0){
            return 0;
        }
        return getLine(location-1);
    }

    public char[] assemble(List<PackGroup> packGroups) throws ParserException {
        List<Character> toReturn = new ArrayList<Character>();
        for(PackGroup pg:packGroups){
            locationToLine.put(toReturn.size(),pg.line);
            toReturn.addAll(assembleExpression(pg));
        }

        char[] buffer = new char[toReturn.size()];
        for(int i=0;i<buffer.length;i++){
            if(toReturn.get(i)==null){
                System.out.println("test");
            }
            buffer[i] = toReturn.get(i);
        }
        return buffer;
    }

    public List<Character> assembleExpression(PackGroup pg){
        List<Character> toReturn = new ArrayList<Character>();
        if(!pg.operator.is("DAT")){
            switch(pg.values.size()){
                case 2:
                    toReturn.add(Builder.makeInstruction(pg.operator,pg.values.get(0),pg.values.get(1)));
                    if(pg.values.get(1).getData()!=null){
                        toReturn.add(pg.values.get(1).getData());
                    }
                    if(pg.values.get(0).getData()!=null){
                        toReturn.add(pg.values.get(0).getData());
                    }
                    break;
                case 1:
                    toReturn.add(Builder.makeSpecialInstruction(pg.operator,pg.values.get(0)));
                    if(pg.values.get(0).getData()!=null){
                        toReturn.add(pg.values.get(0).getData());
                    }
                    break;
                default:
                    toReturn.add(Builder.makeSpecialInstruction(pg.operator, new ValuePack((char) 0x0, 0, "")));
            }
        }
        else{
            for(ValuePack vp:pg.values){
                toReturn.add(vp.getData());
            }
        }
        if(toReturn.contains(null)){
            System.out.println("Shit");
        }

        return toReturn;
    }
}
