package com.prealpha.dcputil.compiler;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 3:45 AM
 */
public class Token {
//    public enum TokenTypes{
//        OPCODE, LABEL, LABEL_REF, REGISTER, REGISTER_REF,
//    }
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
