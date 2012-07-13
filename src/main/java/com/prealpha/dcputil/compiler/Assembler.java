package com.prealpha.dcputil.compiler;

import com.prealpha.dcputil.data.Pack;
import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:41 AM
 */
public class Assembler {
    private List<Expression> expressionList;
    public Assembler(){

    }

    public char[] assemble(List<Expression> expressions) throws ParserException {
        List<Character> toReturn = new ArrayList<Character>(expressions.size()*2);
        this.expressionList = expressions;

        int pointer = 0;
        for(Expression exp:expressionList){
            toReturn.addAll(assembleExpression(exp));
        }

        char[] ret = new char[toReturn.size()];
        for(int i=0;i<toReturn.size();i++){
            ret[i] = toReturn.get(i);
        }

        return ret;
    }

    private List<Character> assembleExpression(Expression exp) throws ParserException {
        List<Character> charList = new ArrayList<Character>(3);

        char op = getOp(exp.tokens[0]);
        char[] bSet  = getVal(exp.tokens[1]);
        char[] aSet  = getVal(exp.tokens[2]);
    }

    private char getOp(Token token) throws ParserException {
        Operator.OperatorPack opPack = Operator.operators.get(token.orig);
        if(opPack != null){
            return opPack.getCode();
        }
        else{
            throw new ParserException("Unable to find the operator\""+token.orig+"\" on line "+token.lineNum,token.lineNum);
        }
    }



    private char[] getVal(Token token) throws ParserException {
        String s = token.orig.toUpperCase();

        Value.ValuePack test = Value.values.get(s);
        if(test != null){
            char[] toReturn = new char[1];
            toReturn[0] = test.getCode();
            return toReturn;
        }

        else{
            if((s.contains("[")&& !s.contains("]")) || (!s.contains("[")&&s.contains("]"))){
                throw new ParserException("Unmatched brackets on line "+token.lineNum,token.lineNum);
            }
            if(s.contains("[")){

            }
            else{
                if(Character.isDigit(s.toCharArray()[0])){

                }
            }
        }
    }

    private char parseChar(String input){
        input = input.trim();
        int value = 0;
        int radix ;

        if(input.toUpperCase().startsWith("0X")){
            radix = 16;
            input = input.substring(2);
        }
        else{
            radix = 10;
        }
        value = Integer.parseInt(input,radix);

        if(value<=Character.MAX_VALUE && value>=Character.MIN_VALUE){
            return (char) value;
        }
        else{
            throw new NumberFormatException();
        }
    }
}
