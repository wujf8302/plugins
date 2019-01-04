package com.plugin.api.img;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.plugin.api.PathUtil;

public class DrawImage extends Frame {
	
	private Image img = null;
	private int x = 0;//x坐标
	private int y = 0;//y坐标

	public static void main(String[] args) {
		
		DrawImage f = new DrawImage();
		f.draw(50,50);
	}

	public void draw(int x,int y) {
		
		//String basePath = DrawImage.class.getResource("").getPath();
		//this.img = this.getToolkit().getImage(basePath + "sq.png"); // 与上面相比位置改变，提前执行
		
		this.img = this.getToolkit().getImage(PathUtil.getPath(DrawImage.class,"sq.png")); // 与上面相比位置改变，提前执行

		this.x = x;
		this.y = y;
		
		setSize(1366, 768);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void paint(Graphics g) {
		//getGraphics().drawImage(img, this.x, this.y, this);
		
		Graphics graphics = getGraphics();
		graphics.drawString("-------------", this.x, this.y);
		
		graphics.drawString("-------------", this.x+10, this.y+10);

	}
}