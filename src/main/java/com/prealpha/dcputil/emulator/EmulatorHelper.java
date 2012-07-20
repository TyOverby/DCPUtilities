package com.prealpha.dcputil.emulator;

import static com.prealpha.dcputil.emulator.Valuecodes.CHAR_MAX;

/**
 * User: Ty
 * Date: 7/20/12
 * Time: 12:30 PM
 */
class EmulatorHelper {
    public static boolean isConditional(char opcode){
        if(opcode>=0x10 && opcode<=0x17){
            return true;
        }
        return false;
    }

    public static char clear(char input, int left, int right){
        input <<= left;
        input >>= left;

        input >>= right;
        return input;
    }
    public static boolean over(int input){
        return input>CHAR_MAX || input<0;
    }
}
