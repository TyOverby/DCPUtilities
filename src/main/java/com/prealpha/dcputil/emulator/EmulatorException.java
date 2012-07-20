package com.prealpha.dcputil.emulator;

/**
 * User: Ty
 * Date: 7/19/12
 * Time: 1:06 PM
 */
public class EmulatorException extends Exception {
    public final char pc;
    public EmulatorException(String error, char programCounter){
        super(error);
        this.pc = programCounter;
    }
}
