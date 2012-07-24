package com.prealpha.dcputil.emulator.devices.lem1802;

import static com.prealpha.dcputil.util.PrintUtilities.printBin;

/**
 * User: Ty
 * Date: 7/23/12
 * Time: 10:48 PM
 */
abstract class Screen {
    private static final int X_RES = 32;
    private static final int Y_RES = 12;
    abstract public void drawPixel(int x, int y);

    public void drawLetter(int position, char[] font){
        int groupX = getX(position,X_RES);
        int groupY = getY(position,X_RES);
        int x = groupX * 4;
        int y = groupY * 8;


        int test = font[0]<<16 | font[1];

        boolean[][] buffer = new boolean[8][4];
        for(int i=32;i>=0;i--){
            int ix = getX(i,8);
            int iy = getY(i,8);
            if(isSet(test,i)){
                buffer[ix][3-iy] = true;
                drawPixel(x+ix,y+3-iy);
            }
        }

        printBin(test);
        System.out.println("=========");
        for(boolean[] columns:buffer){
            for(boolean b:columns){
                if(b){
                    System.out.print("0");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
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

    public static void main(String... args){
        Screen s = new Screen() {
            @Override
            public void drawPixel(int x, int y) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        char[] input = new char[2];
        input[0] = 65289;
        input[1] = 2304;

        s.drawLetter(0, input);
    }
}
