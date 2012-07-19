package com.prealpha.dcputil;

import com.prealpha.dcputil.compiler.assembler.CompilerTest;
import com.prealpha.dcputil.compiler.lexer.Expression;
import com.prealpha.dcputil.compiler.lexer.Lexer;
import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.*;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 5:22 AM
 */
public class Playground extends CompilerTest{

    public static  void main(String... args){
        Playground playground = new Playground();
        String input = "HWN I\n" +
                ":HW_MAP\n" +
                "SUB I, 1\n" +
                "HWQ I\n" +
                "IFE B, 0x7349\n" +
                "  IFE A, 0xf615\n" +
                "    SET [MONITOR], I\n" +
                "IFE B, 0x30cf\n" +
                "  IFE A, 0x7406\n" +
                "    SET [KEYBOARD], I\n" +
                "IFE B, 0x12d0\n" +
                "  IFE A, 0xb402\n" +
                "    SET [CLOCK], I\n" +
                "IFN I, 0\n" +
                "  SET PC, HW_MAP\n" +
                "  \n" +
                "SET A, 3\n" +
                "SET B, 0x1\n" +
                "HWI [MONITOR]\n" +
                "\n" +
                "SET [0x6000], 0\n" +
                "SET [0x6001], 0xFF00\n" +
                "SET Z, 0\n" +
                ":LOOP\n" +
                "SET A, 1\n" +
                "HWI [KEYBOARD]\n" +
                "IFE C, 0x0\n" +
                "\tSET PC, LOOP\n" +
                "IFE C, 0x10\n" +
                "\tSET PC, BS\n" +
                "IFE C, 0x11\n" +
                "\tSET PC, RET\n" +
                "IFE Z, 384\n" +
                "\tSET PC, LOOP\n" +
                "SET [0x7000], C\n" +
                "BOR [0x7000], 0xF000\n" +
                "SET A, 0\n" +
                "SET B, 0x8000\n" +
                "HWI [MONITOR]\n" +
                "SET [0x8000+Z], [0x7000]\n" +
                "SET A, 0\n" +
                "HWI [KEYBOARD]\n" +
                "ADD Z, 1\n" +
                "SET [0x8000+Z], [0x6001]\n" +
                "SET PC, LOOP\n" +
                "\n" +
                ":BS\n" +
                "IFE Z, 0\n" +
                "\tSET PC, LOOP\n" +
                "SUB Z, 1\n" +
                "SET [0x7000], 0x1020\n" +
                "SET A, 0\n" +
                "SET B, 0x8000\n" +
                "HWI [MONITOR]\n" +
                "SET [0x8000+Z], [0x7000]\n" +
                "ADD Z, 1\n" +
                "SET [0x8000+Z], [0x7000]\n" +
                "SUB Z, 1\n" +
                "SET [0x8000+Z], [0x6001]\n" +
                "SET A, 0\n" +
                "HWI [KEYBOARD]\n" +
                "SET PC, LOOP\n" +
                "\n" +
                ":RET\n" +
                "SET [0x8000+Z], 0x1020\n" +
                ":RET_LOOP\n" +
                "ADD Z, 1\n" +
                "SET [0x6000], Z\n" +
                "MOD [0x6000], 32\n" +
                "IFE [0x6000], 0\n" +
                "\tSET PC, RET_CLEAROUT\n" +
                "SET PC, RET_LOOP\n" +
                ":RET_CLEAROUT\n" +
                "SET [0x8000+Z], [0x6001]\n" +
                "SET A, 0\n" +
                "HWI [KEYBOARD]\n" +
                "SET PC, LOOP\n" +
                "\n" +
                ":MONITOR\n" +
                "DAT 0\n" +
                ":KEYBOARD\n" +
                "DAT 0\n" +
                ":CLOCK\n" +
                "DAT 0";


//        Lexer lexer = new Lexer();
//        for(Expression expression: lexer.lex(input)){
//            System.out.println(expression);
//        }

        //input = "SET [MONITOR], I\n:MONITOR\nDAT 0";
        System.out.println(dump(playground.compile(input)).replace("0x","").replace("\t"," ").replace("  "," "));

//                char test = (char) 0x7c21;
//                printOp(test);
    }
}
