package com.prealpha.dcputil.emulator.devices;

import com.prealpha.dcputil.emulator.Machine;

/**
 * User: Ty
 * Date: 7/21/12
 * Time: 8:47 PM
 */
public class Lem1802 implements Device{
    private char screenLocation;
    private char fontLocation;
    private char paletteLocation;

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

    }

    @Override
    public void update(Machine machine) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
