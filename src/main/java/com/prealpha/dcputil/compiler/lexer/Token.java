package com.prealpha.dcputil.compiler.lexer;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:45 AM
 */
public class Token {
    public final String orig;
    public final int lineNum;
    public final int charStart;
    public final int charEnd;

    public Token(String orig, int lineNum, int charStart, int charEnd){
        this.orig = orig;
        this.lineNum = lineNum;
        this.charStart = charStart;
        this.charEnd   = charEnd;
    }

    public String toString(){
        return orig;
    }
}
