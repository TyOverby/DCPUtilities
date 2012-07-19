package com.prealpha.dcputil.compiler.assembler;

import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.dump;
import static org.junit.Assert.assertEquals;

/**
 * User: Ty
 * Date: 7/18/12
 * Time: 2:40 PM
 */
public class MassiveTest extends CompilerTest {

    @Test
    public void compileTextEditor(){
        // DCPU code written by Sam_technologeek
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

        String expected =   "0000: 1a00 88c3 1a20 7c32 7349 7c12 f615 1bc1\n" +
                            "0008: 007b 7c32 30cf 7c12 7406 1bc1 007c 7c32\n" +
                            "0010: 12d0 7c12 b402 1bc1 007d 84d3 7f81 0001\n" +
                            "0018: 9001 8821 7a40 007b 87c1 6000 7fc1 ff00\n" +
                            "0020: 6001 84a1 8801 7a40 007c 8452 7f81 0022\n" +
                            "0028: c452 7f81 0048 c852 7f81 0064 7cb2 0180\n" +
                            "0030: 7f81 0022 0bc1 7000 7fcb f000 7000 8401\n" +
                            "0038: 7c21 8000 7a40 007b 7aa1 7000 8000 8401\n" +
                            "0040: 7a40 007c 88a2 7aa1 6001 8000 7f81 0022\n" +
                            "0048: 84b2 7f81 0022 88a3 7fc1 1020 7000 8401\n" +
                            "0050: 7c21 8000 7a40 007b 7aa1 7000 8000 88a2\n" +
                            "0058: 7aa1 7000 8000 88a3 7aa1 6001 8000 8401\n" +
                            "0060: 7a40 007c 7f81 0022 7ea1 1020 8000 88a2\n" +
                            "0068: 17c1 6000 7fc8 0020 6000 87d2 6000 7f81\n" +
                            "0070: 0073 7f81 0067 7aa1 6001 8000 8401 7a40\n" +
                            "0078: 007c 7f81 0022 0000 0000 0000 0000 0000";

        // Get it formatted to how 0x10co.de likes it
        String actual =  dump(compile(input)).replace("0x","").replace("\t"," ").replace("  "," ").replace("| ","");

        assertEquals(expected,actual);
    }
}
