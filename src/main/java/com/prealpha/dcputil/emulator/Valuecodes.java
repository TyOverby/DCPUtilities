package com.prealpha.dcputil.emulator;

/**
 * User: Ty
 * Date: 7/19/12
 * Time: 12:57 PM
 */
public class Valuecodes {
    public static final int CHAR_MAX = Character.MAX_VALUE;

    public static final char A = 0x00;
    public static final char B = 0x01;
    public static final char C = 0x02;
    public static final char X = 0x03;
    public static final char Y = 0x04;
    public static final char Z = 0x05;
    public static final char I = 0x06;
    public static final char J = 0x07;

    public static final char REGISTER_MAX = J;

    public static final char POINT_A = 0x08;
    public static final char POINT_B = 0x09;
    public static final char POINT_C = 0x0a;
    public static final char POINT_X = 0x0b;
    public static final char POINT_Y = 0x0c;
    public static final char POINT_Z = 0x0d;
    public static final char POINT_I = 0x0e;
    public static final char POINT_J = 0x0f;

    public static final char POINTER_REGISTER_MAX = POINT_J;

    public static final char POINT_A_NEXT = 0x10;
    public static final char POINT_B_NEXT = 0x11;
    public static final char POINT_C_NEXT = 0x12;
    public static final char POINT_X_NEXT = 0x13;
    public static final char POINT_Y_NEXT = 0x14;
    public static final char POINT_Z_NEXT = 0x15;
    public static final char POINT_I_NEXT = 0x16;
    public static final char POINT_J_NEXT = 0x17;

    public static final char POINTER_REGISTER_NEXT_MAX = POINT_J_NEXT;

    public static final char PUSH_POP = 0x18;
    public static final char PEEK = 0x19;
    public static final char PICK = 0x1a;
    public static final char SP = 0x1b;

    public static final char PC = 0x1c;
    public static final char EX = 0x1d;
    public static final char POINT_NEXT = 0x1e;
    public static final char NEXT = 0x1f;

    public static final char LITERAL_MIN = 0x20;
    public static final char LITERAL_MAX = 0x3f;
}
