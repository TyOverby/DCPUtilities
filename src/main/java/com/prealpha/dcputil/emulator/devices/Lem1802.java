package com.prealpha.dcputil.emulator.devices;

import com.prealpha.dcputil.emulator.Machine;

/**
 * User: Ty
 * Date: 7/21/12
 * Time: 8:47 PM
 */
public abstract class Lem1802 implements Device {
    private boolean isConnected = false;

    private char screenLocation;

    private boolean fontSet = false;
    private char fontLocation;

    private boolean palleteSet = false;
    private char paletteLocation;

    private char borderColor;

    public static final char[] defaultFont = {
            47006,14478,
            29228,30196,
            6587,32655,
            34297,45400,
            9262,9216,
            2090,2048,
            8,0,
            2056,2056,
            255,0,
            248,2056,
            2296,0,
            2063,0,
            15,2056,
            255,2056,
            2296,2056,
            2303,0,
            2063,2056,
            2303,2056,
            26163,39372,
            39219,26316,
            65272,57472,
            32543,1793,
            263,8063,
            32992,63742,
            21760,43520,
            21930,21930,
            65450,65365,
            3855,3855,
            61680,61680,
            0,65535,
            65535,0,
            65535,65535,
            0,0,
            95,0,
            768,768,
            15892,15872,
            9835,12800,
            24860,17152,
            13865,30288,
            2,256,
            7202,16640,
            16674,7168,
            5128,5120,
            2076,2048,
            16416,0,
            2056,2048,
            64,0,
            24604,768,
            15945,15872,
            17023,16384,
            25177,17920,
            8777,13824,
            3848,32512,
            10053,14592,
            15945,12800,
            24857,1792,
            13897,13824,
            9801,15872,
            36,0,
            16420,0,
            2068,8704,
            5140,5120,
            8724,2048,
            601,1536,
            15961,24064,
            32265,32256,
            32585,13824,
            15937,8704,
            32577,15872,
            32585,16640,
            32521,256,
            15937,31232,
            32520,32512,
            16767,16640,
            8256,16128,
            32520,30464,
            32576,16384,
            32518,32512,
            32513,32256,
            15937,15872,
            32521,1536,
            15969,32256,
            32521,30208,
            9801,12800,
            383,256,
            16192,32512,
            8032,7936,
            32560,32512,
            30472,30464,
            1912,1792,
            29001,18176,
            127,16640,
            796,24576,
            16767,0,
            513,512,
            32896,32768,
            1,512,
            9300,30720,
            32580,14336,
            14404,10240,
            14404,32512,
            14420,22528,
            2174,2304,
            18516,15360,
            32516,30720,
            1149,0,
            8256,15616,
            32528,27648,
            383,0,
            31768,31744,
            31748,30720,
            14404,14336,
            31764,2048,
            2068,31744,
            31748,2048,
            18516,9216,
            1086,17408,
            15424,31744,
            7264,7168,
            31792,31744,
            27664,27648,
            19536,15360,
            25684,19456,
            2102,16640,
            119,0,
            16694,2048,
            513,513,
            517,512,
    };


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
        this.isConnected = true;
        char a = machine.getRegisters()[0];
        char b = machine.getRegisters()[1];
        switch(a){
            case 0:
                this.screenLocation = b;
                break;
            case 1:
                this.fontLocation = b;
                this.fontSet = true;
                break;
            case 2:
                this.paletteLocation = b;
                this.palleteSet = true;
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


    private void print(char videoPos, Machine machine){
        char letterId = machine.getMemory()[videoPos];
        char[] font;
        if(this.fontSet){
            int displacement = this.fontLocation + letterId*2;
            font = new char[]{machine.getMemory()[displacement],machine.getMemory()[displacement+1]};
        }
        else{
            int displacement = letterId*2;
            font = new char[]{machine.getMemory()[displacement],machine.getMemory()[displacement+1]};
        }

        this.drawLetter(videoPos-this.screenLocation,font);
    }

    @Override
    public void update(Machine machine) {
       if(this.isConnected){
           for(char i=this.screenLocation;i<(this.screenLocation+386);i++){
                print(i,machine);
           }
       }
    }

    private static final int X_RES = 32;
    private static final int Y_RES = 12;

    abstract public void drawPixel(int x, int y);

    public void drawLetter(int position, char[] font){
        int groupX = getX(position,X_RES);
        int groupY = getY(position,X_RES);
        int x = groupX * 4;
        int y = groupY * 8;


        int test = font[0]<<16 | font[1];

        for(int i=32;i>=0;i--){
            int ix = getX(i,8);
            int iy = getY(i,8);
            if(isSet(test,i)){
                drawPixel(x+3-iy,y+ix);
            }
        }
    }

    private static int getX(int position, int width){
        return position % (width);
    }
    private static int getY(int position, int width){
        return position / width;
    }

    private static boolean isSet(int font, int bit){
        return (font & (1 << bit)) != 0;
    }
}
