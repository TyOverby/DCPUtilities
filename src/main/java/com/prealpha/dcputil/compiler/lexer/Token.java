package com.prealpha.dcputil.compiler.lexer;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:45 AM
 */
public class Token {
    public final String orig;
    public final int lineNum;

    public Token(String orig, int lineNum){
        this.orig = orig;
        this.lineNum = lineNum;
    }

    public String toString(){
        return orig;
    }
}
