package com.prealpha.dcputil.emulator;

/**
 * User: Ty
 * Date: 7/19/12
 * Time: 12:13 PM
 */
public class Opcodes {
    public static final char SET = 0x01;

    public static final char ADD = 0x02;
    public static final char SUB = 0x03;
    public static final char MUL = 0x04;
    public static final char MLI = 0x05;
    public static final char DIV = 0x06;
    public static final char DVI = 0x07;
    public static final char MOD = 0x08;
    public static final char MDI = 0x09;
    public static final char AND = 0x0a;
    public static final char BOR = 0x0b;
    public static final char XOR = 0x0c;
    public static final char SHR = 0x0d;
    public static final char ASR = 0x0e;
    public static final char SHL = 0x0f;

    public static final char IFB = 0x10;
    public static final char IFC = 0x11;
    public static final char IFE = 0x12;
    public static final char IFN = 0x13;
    public static final char IFG = 0x14;
    public static final char IFA = 0x15;
    public static final char IFL = 0x16;
    public static final char IFU = 0x17;

    public static final char ADX = 0x1a;
    public static final char SBX = 0x1b;

    public static final char STI = 0x1e;
    public static final char STD = 0x1f;


    public static final char SPECIAL = 0x0;
}
