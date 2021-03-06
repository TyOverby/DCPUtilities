package com.prealpha.dcputil.compiler.lexer;

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
            tokenList.addAll(lexLine(line, ln));
            ln++;
        }

        return tokenList;
    }

    private static final Pattern section = Pattern.compile("(:?\\[? *\\w+\\+?\\w* *\\]?)([;.*]?)");
    public List<Expression> lexLine(String line, int linNum){
        //line = line.trim();
        line = line.replace(","," ");
        if (line.equals(";")) {
            line = "";
        } else {
            line = line.split(";")[0];
        }

        String[] segments = line.split("\\|");
        List<Expression> expressions = new ArrayList<Expression>();
        int total = 0;
        for(String s:segments){
            List<Token> tokens = new ArrayList<Token>(3);

            Matcher matcher = section.matcher(s);

            int start = firstAlphaNumeric(s);
            while(matcher.find(start)){
                String token = matcher.group(1).trim();
                start = matcher.end(1);
                int charS = matcher.start(1)+total;
                tokens.add(new Token(token,linNum,charS,charS+token.length()));
            }

            if(tokens.size()>0){
                expressions.add(new Expression(tokens.toArray(new Token[tokens.size()])));
            }
            total+=s.length()+1;
        }
        return expressions;
    }

    private int firstAlphaNumeric(String s){
        for(int i=0;i<s.length();i++){
            if(!Character.isWhitespace(s.charAt(i))){
                return i;
            }
        }
        return 0;
    }
}

