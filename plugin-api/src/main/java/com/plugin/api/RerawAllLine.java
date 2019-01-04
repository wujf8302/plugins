package com.plugin.api;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 画线程序
 */
class MyLine {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    public MyLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public void drawMe(Graphics g) {
        g.drawLine(x1, y1, x2, y2);
    }
}

public class RerawAllLine extends Frame {
    Vector vLines = new Vector();
    
    public static void main(String[] args) {
        RerawAllLine f = new RerawAllLine();
        f.init();
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        Enumeration e = vLines.elements();
        while (e.hasMoreElements()) {
            MyLine ln = (MyLine) e.nextElement();
            ln.drawMe(g);
        }
    }
    
    public void init() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ((Window) e.getSource()).dispose();
                System.exit(0);
            }
        });
        
        addMouseListener(new MouseAdapter() {
            int orgX;
            int orgY;
            
            public void mousePressde(MouseEvent e) {
                orgX = e.getX();
                orgY = e.getY();
            }
            
            public void mouseReleased(MouseEvent e) {
                Color c = new Color(250, 150, 0);
                Graphics g = e.getComponent().getGraphics();
                g.setColor(c);
                g.drawLine(orgX, orgY, e.getX(), e.getY());
                vLines.add(new MyLine(orgX, orgY, e.getX(), e.getY()));
            }
        });
        this.setSize(300, 300);
        setVisible(true);
    }
}
