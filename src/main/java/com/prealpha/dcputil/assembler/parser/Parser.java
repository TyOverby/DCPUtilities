package com.prealpha.dcputil.assembler.parser;

import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Operator.OperatorPack;
import com.prealpha.dcputil.info.Value;
import com.prealpha.dcputil.info.Value.ValuePack;
import com.prealpha.dcputil.assembler.lexer.Expression;
import com.prealpha.dcputil.assembler.lexer.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 8:56 AM
 */
public class Parser {
    private int counter = 0;
    private Map<String,Integer> labelToLine = new HashMap<String,Integer>();
    private Map<ValuePack,String> packToLable = new HashMap<ValuePack, String>();

    public List<PackGroup> parse(List<Expression> expressions) throws ParserException {
        List<PackGroup> toReturn = new ArrayList<PackGroup>(expressions.size());
        for(Expression expression:expressions){
            PackGroup pg = parseExpression(expression);
            if(pg!=null){
                toReturn.add(pg);
            }
        }
        for(ValuePack vp:packToLable.keySet()){
            for(String s:labelToLine.keySet()){
                if(s.equals(packToLable.get(vp))){
                    vp.setData((char)(int)labelToLine.get(s));
                }
            }
        }
        return toReturn;
    }

    private PackGroup parseExpression(Expression expression) throws ParserException {
        int start = 0;
        Token first = expression.tokens[0];
        // If it is a label
        if(first.orig.contains(":")){
            String stripped = first.orig.replace(":","");
            if(!labelToLine.containsKey(stripped)){
                labelToLine.put(first.orig.replace(":", ""), ++counter);
            }
            else{
                throw new ParserException("Duplicate labels for: \""+stripped+"\" found at "+labelToLine.get(stripped)+" and "+first.lineNum,first.lineNum);
            }
            start = 1;
        }

        if(expression.tokens.length>1){
            OperatorPack op = Operator.operators.get(expression.tokens[start].orig).clone();
            if(op==null){
                throw new ParserException("Count not find the Operator"+expression.tokens[0].orig,expression.tokens[0].lineNum);
            }
            counter++;
            List<ValuePack> values = new ArrayList<ValuePack>();
            for(int i=start+1;i<expression.tokens.length;i++){
                if(!op.is("DAT")){
                    values.add((getValue(expression.tokens[i], i)));
                }
                else{
                    values.add(Value.values.get("data-literal").withData(parseSingular(expression.tokens[i].orig,first.lineNum)));
                }
            }

            return new PackGroup(op,values.toArray(new ValuePack[0]));
        }
        else{
            return null;
        }
    }

    private ValuePack getValue(Token token, int position) throws ParserException {
        String original = token.orig;
        int line = token.lineNum;

        ValuePack value = Value.values.get(original);
        // If it exists in the valuePack
        if(value != null){
            // If it is in the "B" position and it is a literal, disallow it
            if(!(position==1&&value.is("literal"))){
                return value;
            }
            else{
                return Value.values.get("next").withData((char)(value.getCode()-0x21));
            }
        }

        counter++;
        boolean isPointer = false;
        boolean hasPlus = false;
        boolean containsNumber = false;
        char number = 0;

        // Check for pointer
        if((original.contains("[")&&!original.contains("]"))||(!original.contains("[")&&original.contains("]"))){
            throw new ParserException("Missing matching brackets",token.lineNum);
        }
        isPointer = original.contains("[");
        // Check for plus
        hasPlus = original.contains("+");
        // Check if it is a number
        containsNumber = isNumber(original.replace("[","").replace("]",""));
        if(containsNumber){
            number = parseSingular(original.replace("[","").replace("]",""),line);
        }

        if(hasPlus){
            String[] split = original.replace("[","").replace("]","").split("\\+");
            return Value.values.get("["+split[0]+"+next]").withData(parseSingular(split[1],line));
        }

        String build ="";
        if(isPointer){
            build = "[next]";
        }
        else
        {
            build = "next";
        }

        if(containsNumber){
            return Value.values.get(build).withData(number);
        }
        else{
            ValuePack toReturn = Value.values.get(build).clone();
            packToLable.put(toReturn,original.replace("[","").replace("]",""));
            return toReturn;
        }
    }

    private Character getInner(String input,int line) throws ParserException {
        input = input.trim();
        if(isNumber(input)){
            return parseSingular(input,line);
        }
        else{
            // We assume that this is a label
            return null;
        }
    }
    static final Pattern numberPattern = Pattern.compile("^((0x\\w+)|(\\d+)|(b(1|0)+))$");
    private boolean isNumber(String input){
        return numberPattern.matcher(input).matches();
    }
    private char parseSingular(String input,int lineNum) throws ParserException {
        input = input.trim();
        int value = 0;
        int radix ;

        if(input.toUpperCase().startsWith("0X")){
            radix = 16;
            input = input.substring(2);
        }
        else if(input.toUpperCase().startsWith("B")){
            radix = 2;
            input = input.substring(1);
        }
        else{
            radix = 10;
        }
        try{
            value = Integer.parseInt(input,radix);
        }catch (NumberFormatException nfe){
            throw new ParserException("Number can not be parsed",lineNum);
        }

        if(value<=Character.MAX_VALUE && value>=Character.MIN_VALUE){
            return (char) value;
        }
        else{
            throw new ParserException("Number is either above ",lineNum);
        }
    }
}
