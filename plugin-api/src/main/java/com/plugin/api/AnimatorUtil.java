package com.plugin.api;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * 实现图片的连续播放
 */
public class AnimatorUtil extends JPanel implements ActionListener,Serializable{

	private ImageIcon images[];
	private int totalImages = 30;
	private int currentImage = 0;
	private int animationDelay = 30; 
	private Timer animationTimer;

	public AnimatorUtil(Class clazz,String basePath,String fileType) {
		
		setSize(getPreferredSize());
		images = new ImageIcon[totalImages];
		for (int i = 0; i < images.length; ++i) {
			String fileName = PathUtil.getPath(clazz,basePath + i + fileType);
			if(fileName != null && !"".equals(fileName)){
				images[i] = new ImageIcon(fileName);
				System.out.println(String.valueOf((i+1))+": "+images[i].getImage());
			}
		}

		startAnimation();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        if(images[currentImage] != null){
    		if (images[currentImage].getImageLoadStatus() == MediaTracker.COMPLETE) {
    			images[currentImage].paintIcon(this, g, 0, 0);
    			currentImage = (currentImage + 1) % totalImages;
    		}
        }
        currentImage++;
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void startAnimation() {
		if (animationTimer == null) {
			currentImage = 0;
			animationTimer = new Timer(animationDelay, this);
			animationTimer.start();
		} else{ 
		    if (!animationTimer.isRunning()){
			     animationTimer.restart();
		    }
		}
	}

	public void stopAnimation() {
		animationTimer.stop();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getPreferredSize() {
		return new Dimension(160, 80);
	}
}
