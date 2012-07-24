package com.prealpha.dcputil.emulator.devices.lem1802;

import javax.swing.*;
import java.awt.*;

/**
 * User: Ty
 * Date: 7/23/12
 * Time: 11:59 PM
 */
public class SwingScreen extends Screen{
    private JPanel jPanel;
    public SwingScreen(){
        jPanel = new JPanel();
    }
    public JPanel getPanel(){
        return jPanel;
    }

    @Override
    public void drawPixel(int x, int y) {
        Graphics g = jPanel.getGraphics();
        g.setColor(Color.black);
        g.fillRect(x,y,1,1);
    }

    public static void main(String... args){

    }
}
