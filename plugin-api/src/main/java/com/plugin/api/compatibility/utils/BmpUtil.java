package com.plugin.api.compatibility.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * bmp格式的图片转换为jpg格式的图片.
 */
@SuppressWarnings({ "restriction" })
public class BmpUtil {
	
	public static String BMPNM = ".bmp";
	
	/**
	 * 生成图片文件名
	 * @param iamge
	 * @return
	 */
	public static String getFileName(String type){
		String uid = BmpUtil.randomString();
		return uid+type;
	}
	
	public static final String randomString() {
		String result = null;
		UUID uuid = UUID.randomUUID();
		if(uuid!=null){
			result = uuid.toString();
			result = result.replace("-","");
		}	
		return result;
	}
	
	
    /**
     * 图片格式转换 BMP -> JPG
     * @param file
     * @param dstFile
     */
	public static void bmpTojpg(String file, String dstFile) {
        try {
            FileInputStream in = new FileInputStream(file);
            Image TheImage = read(in);
            int wideth = TheImage.getWidth(null);
            int height = TheImage.getHeight(null);
            BufferedImage tag = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(TheImage, 0, 0, wideth, height, null);
            FileOutputStream out = new FileOutputStream(dstFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static int constructInt(byte[] in, int offset) {
        int ret = ((int) in[offset + 3] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 2] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 1] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 0] & 0xff);
        return (ret);
    }
 
    public static int constructInt3(byte[] in, int offset) {
        int ret = 0xff;
        ret = (ret << 8) | ((int) in[offset + 2] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 1] & 0xff);
        ret = (ret << 8) | ((int) in[offset + 0] & 0xff);
        return (ret);
    }
 
    public static long constructLong(byte[] in, int offset) {
        long ret = ((long) in[offset + 7] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 6] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 5] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 4] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 3] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 2] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 1] & 0xff);
        ret |= (ret << 8) | ((long) in[offset + 0] & 0xff);
        return (ret);
    }
 
    public static double constructDouble(byte[] in, int offset) {
        long ret = constructLong(in, offset);
        return (Double.longBitsToDouble(ret));
    }
 
    public static short constructShort(byte[] in, int offset) {
        short ret = (short) ((short) in[offset + 1] & 0xff);
        ret = (short) ((ret << 8) | (short) ((short) in[offset + 0] & 0xff));
        return (ret);
    }
 
    static class BitmapHeader {
        public int iSize, ibiSize, iWidth, iHeight, iPlanes, iBitcount,
                iCompression, iSizeimage, iXpm, iYpm, iClrused, iClrimp;
 
        // 读取bmp文件头信息
        public void read(FileInputStream fs) throws IOException {
            final int bflen = 14;
            byte bf[] = new byte[bflen];
            fs.read(bf, 0, bflen);
            final int bilen = 40;
            byte bi[] = new byte[bilen];
            fs.read(bi, 0, bilen);
            iSize = constructInt(bf, 2);
            ibiSize = constructInt(bi, 2);
            iWidth = constructInt(bi, 4);
            iHeight = constructInt(bi, 8);
            iPlanes = constructShort(bi, 12);
            iBitcount = constructShort(bi, 14);
            iCompression = constructInt(bi, 16);
            iSizeimage = constructInt(bi, 20);
            iXpm = constructInt(bi, 24);
            iYpm = constructInt(bi, 28);
            iClrused = constructInt(bi, 32);
            iClrimp = constructInt(bi, 36);
        }
    }
 
    public static Image read(FileInputStream fs) {
        try {
            BitmapHeader bh = new BitmapHeader();
            bh.read(fs);
            if (bh.iBitcount == 24) {
                return (readImage24(fs, bh));
            }
            if (bh.iBitcount == 32) {
                return (readImage32(fs, bh));
            }
            fs.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return (null);
    }
 
    // 24位
    protected static Image readImage24(FileInputStream fs, BitmapHeader bh)
            throws IOException {
        Image image;
        if (bh.iSizeimage == 0) {
            bh.iSizeimage = ((((bh.iWidth * bh.iBitcount) + 31) & ~31) >> 3);
            bh.iSizeimage *= bh.iHeight;
        }
        int npad = (bh.iSizeimage / bh.iHeight) - bh.iWidth * 3;
        int ndata[] = new int[bh.iHeight * bh.iWidth];
        byte brgb[] = new byte[(bh.iWidth + npad) * 3 * bh.iHeight];
        fs.read(brgb, 0, (bh.iWidth + npad) * 3 * bh.iHeight);
        int nindex = 0;
        for (int j = 0; j < bh.iHeight; j++) {
            for (int i = 0; i < bh.iWidth; i++) {
                ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(
                        brgb, nindex);
                nindex += 3;
            }
            nindex += npad;
        }
        image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0,
                        bh.iWidth));
        fs.close();
        return (image);
    }
 
    // 32位
    protected static Image readImage32(FileInputStream fs, BitmapHeader bh)
            throws IOException {
        Image image;
        int ndata[] = new int[bh.iHeight * bh.iWidth];
        byte brgb[] = new byte[bh.iWidth * 4 * bh.iHeight];
        fs.read(brgb, 0, bh.iWidth * 4 * bh.iHeight);
        int nindex = 0;
        for (int j = 0; j < bh.iHeight; j++) {
            for (int i = 0; i < bh.iWidth; i++) {
                ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(
                        brgb, nindex);
                nindex += 4;
            }
        }
        image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0,
                        bh.iWidth));
        fs.close();
        return (image);
    }
 
    public static void main(String[] args) {
        String srcfile = "E:\\1.bmp";
        String dstFile = "E:\\1.jpg";
        bmpTojpg(srcfile, dstFile);
    }
 
}