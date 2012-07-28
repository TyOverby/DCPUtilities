package com.prealpha.dcputil.emulator.devices.lem1802;

import javax.swing.*;
import java.awt.*;

/**
 * User: Ty
 * Date: 7/23/12
 * Time: 11:59 PM
 */
public class SwingLem1802 extends Lem1802 {
    private JFrame jFrame;
    private JPanel jPanel;
    private final int pixelSize;

    public SwingLem1802(){
        this(1);
    }
    public SwingLem1802(int size){
        jPanel = new JPanel();
        this.pixelSize = size;
    }
    public JPanel getPanel(){
        return jPanel;
    }

    public JFrame getWrapped(){
        if(jFrame==null){
            jFrame = new JFrame();
            jFrame.setBounds(0,0,300,300);
            jFrame.getContentPane().add(jPanel);
            jPanel.setBounds(0,0,300,300);
            return jFrame;
        }
        else{
            return jFrame;
        }
    }

    @Override
    public void drawPixel(int x, int y) {
        Graphics g = jPanel.getGraphics();
        g.setColor(Color.black);
        g.fillRect(x*pixelSize,y*pixelSize,pixelSize,pixelSize);
    }

    @Override
    public void clear() {
        Graphics g = jPanel.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,this.jPanel.getWidth(),this.jPanel.getHeight());
    }

    public static void main(String... args){
        JFrame jf = new JFrame();
        SwingLem1802 ss = new SwingLem1802(3);
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
