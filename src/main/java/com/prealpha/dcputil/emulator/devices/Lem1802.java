package com.prealpha.dcputil.emulator.devices;

import com.prealpha.dcputil.emulator.Machine;

/**
 * User: Ty
 * Date: 7/21/12
 * Time: 8:47 PM
 */
public class Lem1802 implements Device {
    private char screenLocation;
    private char fontLocation;
    private char paletteLocation;
    private char borderColor;

    @Override
    public char[] getId() {
        final int id = 0x7349f615;
        final char first  = (char) (id<<16)>>16;
        final char second = (char) (id>>16);
        final char[] toReturn = {first,second};
        return toReturn;
    }

    @Override
    public char[] getManufacturer() {
        final int manufacturer = 0x1c6c8b36 ;
        final char first  = (char) (manufacturer<<16)>>16;
        final char second = (char) (manufacturer>>16);
        final char[] toReturn = {first,second};
        return toReturn;
    }

    @Override
    public char getVersion() {
        return 0x1802;
    }

    @Override
    public void hwi(Machine machine) {
        char a = machine.getRegisters()[0];
        char b = machine.getRegisters()[1];
        switch(a){
            case 0:
                this.screenLocation = b;
                break;
            case 1:
                this.fontLocation = b;
                break;
            case 2:
                this.paletteLocation = b;
                break;
            case 3:
                this.borderColor = b;
                break;
            case 4:
                System.err.println("MEM_DUMP_FONT not yet supported");
                break;

            case 5:
                System.err.println("MEM_DUMP_PALETTE not yet supported");
                break;
        }
    }

    @Override
    public void update(Machine machine) {

    }
}
