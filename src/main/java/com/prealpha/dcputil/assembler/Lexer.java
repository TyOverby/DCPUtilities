package com.prealpha.dcputil.assembler;

import com.prealpha.dcputil.assembler.lexer.Expression;
import com.prealpha.dcputil.assembler.lexer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:45 AM
 */
public class Lexer {
    private String inputString = "";

    public Lexer(){

    }

    public List<Expression> lex(String input){
        this.inputString = input;
        List<Expression> tokenList = new ArrayList<Expression>();

        String[] lines = input.split("\n");
        int ln = 1;
        for(String line:lines){
            Expression exp = lexLine(line, ln);
            if(exp.tokens.length>0){
                tokenList.add(exp);
            }
            ln++;
        }

        return tokenList;
    }

    private static final Pattern section = Pattern.compile("(\\[? *\\w+\\+?\\w* *\\]?)([;.*]?)");
    public Expression lexLine(String line, int linNum){
        line = line.replace(","," ");
        line = line.split(";")[0];

        List<Token> tokens = new ArrayList<Token>(3);

        Matcher matcher = section.matcher(line);
        int start = 0;
        while(matcher.find(start)){
            String token = matcher.group(1).trim();
            start = matcher.end(1);
            tokens.add(new Token(token,linNum));
        }
        return new Expression(tokens.toArray(new Token[0]));
    }

    public static void main(String... args){
        Lexer lexer = new Lexer();
        System.out.println(lexer.lexLine("SET [A] 5; This is a test",0));
    }
}

