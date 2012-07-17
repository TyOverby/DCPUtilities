package com.prealpha.dcputil.assembler.parser;

/**
 * User: Ty
 * Date: 7/12/12
 * Time: 6:13 AM
 */
public class ParserException extends Exception {
    public final int lineNum;
    ParserException(String message, int lineNumber){
        super(message);
        this.lineNum = lineNumber;
    }
}
