package com.prealpha.dcputil.emulator.devices.lem1802;

import com.prealpha.dcputil.emulator.devices.Lem1802;

import javax.swing.*;
import java.awt.*;

/**
 * User: Ty
 * Date: 7/23/12
 * Time: 11:59 PM
 */
public class SwingScreen extends Lem1802 {
    private JPanel jPanel;
    final int pixelSize;

    public SwingScreen(){
        this.pixelSize = 0;
    }
    public SwingScreen(int size){
        jPanel = new JPanel();
        this.pixelSize = size;
    }
    public JPanel getPanel(){
        return jPanel;
    }

    @Override
    public void drawPixel(int x, int y) {
        Graphics g = jPanel.getGraphics();
        g.setColor(Color.black);
        g.fillRect(x*pixelSize,y*pixelSize,pixelSize,pixelSize);
    }

    public static void main(String... args){
        JFrame jf = new JFrame();
        SwingScreen ss = new SwingScreen(3);
        jf.getContentPane().add(ss.getPanel());
        ss.getPanel().setBounds(0,0,200,200);
        jf.setBounds(0,0,200,200);
        jf.setVisible(true);
        while(jf.isVisible()){
            ss.update(null);
        }
        System.exit(1);
    }
}
