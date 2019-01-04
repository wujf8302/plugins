package com.plugin.api.compatibility.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 * 生成缩略图
 */
public class ImageUtil {


	private static Logger log = Logger.getLogger(ImageUtil.class);
	
	public ImageUtil() {
		
	}

	public static BufferedImage resizeImage(File image, int maxWidth,int maxHeight, boolean absolute) throws IOException {
		BufferedImage source = ImageIO.read(image);
		int imageWidth = source.getWidth();
		int imageHeight = source.getHeight();
		float aspectRadio = (float) imageWidth / (float) imageHeight;
		if (!absolute) {
			if ((float) maxWidth / (float) maxHeight > aspectRadio) {
				imageWidth = (int) Math.ceil((float) maxHeight * aspectRadio);
				imageHeight = maxHeight;
			} else if ((float) maxWidth / (float) maxHeight < aspectRadio) {
				imageHeight = (int) Math.ceil((float) maxWidth / aspectRadio);
				imageWidth = maxWidth;
			} else {
				imageHeight = maxHeight;
				imageWidth = maxWidth;
			}
		} else {
			float reSizeRadio = (float) maxWidth / (float) maxHeight;
			if (reSizeRadio > aspectRadio) {
				imageWidth = maxWidth;
				imageHeight = (int) Math.ceil((float) imageWidth / aspectRadio);
			} else if ((float) maxWidth / (float) maxHeight < aspectRadio) {
				imageWidth = (int) Math.ceil((float) maxHeight * aspectRadio);
				imageHeight = maxHeight;
			} else {
				imageWidth = maxWidth;
				imageHeight = maxHeight;
			}
		}
		int type = source.getType();
		BufferedImage target = null;
		if (type == 0) {
			ColorModel cm = source.getColorModel();
			java.awt.image.WritableRaster raster = cm
					.createCompatibleWritableRaster(imageWidth, imageHeight);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(imageWidth, imageHeight, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(
				(float) imageWidth / (float) source.getWidth(),
				(float) imageHeight / (float) source.getHeight()));
		g.dispose();
		System.out.println(target);
		if (absolute)
			target = grabImage(target, 0, 0, maxWidth, maxHeight);
		return target;
	}

	public static void resizeImage(File image, File dest, String name,
			int maxWidth, int maxHeight, boolean absolute) throws IOException {
		if (name == null)
			name = image.getName();
		String abName = (new StringBuilder(String.valueOf(dest
				.getAbsolutePath()))).append("/").append(name).toString();
		File newImage = new File(abName);
		String type = name.substring(name.lastIndexOf(".") + 1);
		saveImage(resizeImage(image, maxWidth, maxHeight, absolute), newImage,
				type);
	}

	public static void saveImage(BufferedImage image, File toFile,
			String formatName) throws IOException {
		if (formatName.toUpperCase().equals("GIF"))
			formatName = "jpg";
		toFile.mkdirs();
		ImageIO.write(image, formatName, toFile);
	}

	public static void flushImage(BufferedImage image, OutputStream os)
			throws IOException {
		ImageIO.write(image, "jpg", os);
	}

	public static BufferedImage grabImage(File image, int posX1, int posY1,
			int posX2, int posY2) throws IOException {
		BufferedImage source = ImageIO.read(image);
		return grabImage(source, posX1, posY1, posX2, posY2);
	}

	public static BufferedImage grabImage(BufferedImage source, int posX1,int posY1, int posX2, int posY2) throws IOException {
		int imageWidth = source.getWidth();
		int imageHeight = source.getHeight();
		int grabWidth = posX2 - posX1;
		int grabHeight = posY2 - posY1;
		if (posX2 > imageWidth || posY2 > imageHeight)
			throw new IOException("生成缩略图失败！");
		else
			return source.getSubimage(posX1, posX1, grabWidth, grabHeight);
	}

	public static void grabImage(File image, File dest, String name, int posX1,int posY1, int posX2, int posY2) throws IOException {
		if (name == null)
			name = image.getName();
		String abName = (new StringBuilder(String.valueOf(dest.getAbsolutePath()))).append(name).toString();
		File newImage = new File(abName);
		String type = image.getName().substring(image.getName().lastIndexOf(".") + 1);
		newImage.createNewFile();
		saveImage(grabImage(image, posX1, posY1, posX2, posY2), newImage, type);
	}

	public static void createWatermark(InputStream is, File watermark,OutputStream os, float contrast) throws IOException {
		BufferedImage source = ImageIO.read(is);
		BufferedImage waterImage = ImageIO.read(watermark);
		int swidth = source.getWidth();
		int sheight = source.getHeight();
		BufferedImage dest = new BufferedImage(swidth, sheight, 1);
		Graphics2D g2D = dest.createGraphics();
		g2D.setBackground(Color.white);
		g2D.drawImage(source, 0, 0, null);
		g2D.setComposite(AlphaComposite.getInstance(3, contrast));
		int wwidth = waterImage.getWidth();
		int wheight = waterImage.getHeight();
		for (int i = 0; i < swidth; i += wwidth) {
			for (int j = 0; j < sheight; j += wheight)
				g2D.drawImage(waterImage, i, j, null);
		}
		g2D.dispose();
		com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(os);
		com.sun.image.codec.jpeg.JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(dest);
		param.setQuality(80F, true);
		encoder.encode(dest);
	}

	public static void createSimpleWatermart(File source, File watermark,
			File dest, String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(source);
		String path = dest.getAbsolutePath();
		File outFile = new File((new StringBuilder(String.valueOf(path)))
				.append(fileName).toString());
		FileOutputStream fos = new FileOutputStream(outFile);
		createWatermark(fis, watermark, fos, 0.2F);
	}

	public static BufferedImage createVerifyImage(String word) {
		int height = 20;
		int width = 15 * word.length();
		BufferedImage image = new BufferedImage(width, height, 1);
		Graphics gra = image.getGraphics();
		Random random = new Random();
		gra.setColor(getRandColor(200, 250));
		gra.fillRect(0, 0, width, height);
		gra.setColor(Color.black);
		Font mFont = new Font("Times New Roman", 0, 18);
		gra.setFont(mFont);
		gra.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gra.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < word.length(); i++) {
			gra.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			gra.drawString(String.valueOf(word.charAt(i)), 13 * i + 6, 16);
		}

		return image;
	}

	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static void main(String arg[]) throws IOException {
		File image = new File("C:\\Documents and Settings\\dell\\\u684C\u9762\\wfs_img\\bb.gif");
		File dest = new File("D:/");
		resizeImage(image, dest, "aa.gif", 640, 480, true);
	}

}
