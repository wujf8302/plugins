package com.plugin.api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * 截屏工具.
 * @author wujf
 */
public class ScreenUtil extends JFrame {

	private static final long serialVersionUID = -267804510087895906L;

	private JButton button = null;

	private JLabel imgLabel = null;

	public ScreenUtil() {
		button = new JButton("模拟屏幕（点右键退出）");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new ScreenWindow(imgLabel);
				} catch (Exception e1) {
					JOptionPane.showConfirmDialog(null, "出现意外错误！", "系统提示",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JPanel pane = new JPanel();
		pane.setBackground(Color.WHITE);
		imgLabel = new JLabel();
		pane.add(imgLabel);
		JScrollPane spane = new JScrollPane(pane);
		this.getContentPane().add(button, BorderLayout.NORTH);
		this.getContentPane().add(spane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new ScreenUtil();
	}
}

class ScreenWindow extends JFrame {

	private static final long serialVersionUID = -3758062802950480258L;

	private boolean isDrag = false;

	private int x = 0;

	private int y = 0;

	private int xEnd = 0;

	private int yEnd = 0;

	public ScreenWindow(final JLabel imgLabel) throws AWTException,
			InterruptedException {
		Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
		JLabel label = new JLabel(new ImageIcon(ScreenImage.getScreenImage(0,
				0, screenDims.width, screenDims.height)));
		label.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					dispose();
				}
			}

			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				if (isDrag) {
					xEnd = e.getX();
					yEnd = e.getY();
					if (x > xEnd) {
						int temp = x;
						x = xEnd;
						xEnd = temp;
					}
					if (y > yEnd) {
						int temp = y;
						y = yEnd;
						yEnd = temp;
					}
					try {
						imgLabel.setIcon(new ImageIcon(ScreenImage
								.getScreenImage(x, y, xEnd - x, yEnd - y)));
					} catch (Exception ex) {
						JOptionPane.showConfirmDialog(null, "出现意外错误！", "系统提示",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				}
			}
		});
		label.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (!isDrag)
					isDrag = true;
			}

			public void mouseMoved(MouseEvent e) {
				/** 拖动过程的虚线选取框需自己实现 */
			}
		});
		this.setUndecorated(true);
		this.getContentPane().add(label);
		this.setSize(screenDims.width, screenDims.height);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

class ScreenImage {

	public static Image getScreenImage(int x, int y, int w, int h)
			throws AWTException, InterruptedException {
		Robot robot = new Robot();
		Image screen = robot.createScreenCapture(new Rectangle(x, y, w, h))
				.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		MediaTracker tracker = new MediaTracker(new Label());
		tracker.addImage(screen, 1);
		tracker.waitForID(0);
		return screen;
	}
}
