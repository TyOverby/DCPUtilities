package com.prealpha.dcputil.compiler;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 4:36 AM
 */
public class Expression {
    public final Token[] tokens;

    public Expression(Token... tokens){
        this.tokens = tokens;
    }

    public String toString(){
        String total = "";
        for(Token t:tokens){
            total+= t.toString()+" ";
        }
        return total;
    }
}
