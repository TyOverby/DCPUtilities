package com.prealpha.dcputil.compiler.lexer;

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
        boolean first = true;
        for(Token t:tokens){
            if(!first){
                total+="\t";
            }
            first = false;
            total+= t.toString()+"\n";
        }
        return total;
    }
}
