package com.plugin.api.native2ascii;
import java.io.BufferedReader;   
import java.io.BufferedWriter;   
import java.io.File;   
import java.io.FileFilter;   
import java.io.FileReader;   
import java.io.FileWriter;   
import java.io.IOException;   
import java.util.regex.Pattern;   
  
/**
 * 将利用 native2ascii 命令编码的资源文件反向. <br>  
 * native2ascii < resource.properties > resource_zh_CN.properties<br>  
 * java -jar UncodeReverse.jar<br>  
 * @author wujf 
 */  
public class UncodeReverse {   
  
    private static final String VERSION = "v1.0f";   
  
    private static boolean SkipComment = false;   
  
    public static void main(String args[]) {   
        String path = null;   
        /**
         * 默认匹配所有的资源文件  
         */  
        String fileName = null;   
        for (int i = 0; i < args.length; i++) {   
            if (args[i].startsWith("-S")) {   
                SkipComment = true;   
            } else {   
                if (path == null) {   
                    path = args[i];   
                } else if (fileName == null) {   
                    fileName = args[i];   
                }   
            }   
        }   
        if (path == null) {   
            usage();   
            System.exit(-1);   
        }   
        if (fileName == null) {   
            fileName = "*.properties";   
        }   
        File file = new File(path);   
        if (!file.isDirectory()) {   
            System.out.println("[" + file + "] not exists or not directory.");   
            usage();   
            System.exit(-1);   
        }   
        reverseProperties(file, new UncodeFileFilter(fileName));   
    }   
  
    private static File[] reverseProperties(File file, FileFilter filter) {   
        File[] files = file.listFiles(filter);   
        for (int i = 0; i < files.length; i++) {   
            if (files[i].isDirectory()) {   
                reverseProperties(files[i], filter);   
            } else {   
                File source = files[i];   
                /** 
                 * 文件名后添加.reverse后缀.  
                 */  
                File target = new File(files[i].getParent(), files[i].getName()+ ".reverse");   
                // if (target.exists()) {   
                //      System.out.println("Target file [" + target +"] exits,break.");   
                // } else {   
                if (decodeFile(source, target)) {   
                    System.out.println(source + "->" + target.getName()   
                            + " success.");   
                } else {   
                    System.out.println(source + " error.");   
                    target.delete();   
                }   
                // }   
            }   
        }   
        return files;   
    }   
  
    private static boolean decodeFile(File source, File target) {   
        BufferedReader read = null;   
        BufferedWriter writer = null;   
        try {   
            read = new BufferedReader(new FileReader(source));   
            writer = new BufferedWriter(new FileWriter(target));   
            writer.write("# Reverse by UncodeReverse " + VERSION  + ". Copyright 2007 ZhaoHonghui (zhaohonghui@hotmail.com) " + "http://blog.csdn.net/z3h .");   
            writer.newLine();   
            writer.write("# Source File Name:" + source.getAbsolutePath());   
            writer.newLine();   
            String s = read.readLine();   
            while (s != null) {   
                String t = decodeString(s);   
                writer.write(t);   
                writer.newLine();   
                s = read.readLine();   
            }   
            return true;   
        } catch (IOException e) {   
            System.out.println(e.getClass() + ":" + e.getMessage());   
            return false;   
        } finally {   
            try {   
                if (read != null) {   
                    read.close();   
                }   
                if (writer != null) {   
                    writer.close();   
                }   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    }   
  
    private static String decodeString(String str) {   
        if (SkipComment && str.trim().startsWith("#")) {   
            return str;   
        }   
        StringBuffer buf = new StringBuffer(str.length());   
        boolean flag = false;   
        boolean uflag = false;   
        int cnt = 0;   
        char[] uncode = new char[4];   
        for (int i = 0; i < str.length(); i++) {   
            char c = str.charAt(i);   
            if (c == '\\') {   
                flag = true;   
                continue;   
            }   
            if (flag && c == 'u') {   
                uflag = true;   
                continue;   
            } else {   
                 if (flag && !uflag) {   
                    buf.append("\\");   
                }   
                flag = false;   
            }   
            if (uflag && cnt < 5) {   
                uncode[cnt] = c;   
                cnt++;   
                if (uflag && cnt == 4) {   
                    try {   
                        buf.append((char) Integer.parseInt(new String(uncode),16));   
                    } catch (NumberFormatException Nfe) {   
                        buf.append("\\u" + new String(uncode));   
                    }   
                    flag = false;   
                    uflag = false;   
                    cnt = 0;   
                }   
            } else {   
                buf.append(c);   
            }   
        }   
        return buf.toString();   
    }   
  
    private static void usage() {   
        System.out.println("UncodeReverse "+ VERSION+ ". Copyright 2007 ZhaoHonghui (zhaohonghui@hotmail.com) http://blog.csdn.net/z3h.");   
        System.out.println("Usage:UncodeReverse [option(s)] <path> [fileName(s)]");   
        System.out.println("    Options  -S:Skip Comment.");   
        System.out.println("    FileName(s):'*.properties','*_zh.properties','resource*_zh_CN.properties'");   
        System.out.println("Sample:UncodeReverse D:/src *_zh.properties");   
    }   
  
    static class UncodeFileFilter implements FileFilter {   
        String fileNameExtra;   
  
        UncodeFileFilter(String fileNameExtra) {   
            /**
             *  将. 替换为 . 将* 替换为.*  
             */  
        	fileNameExtra = fileNameExtra.replaceAll("\\.", "\\.");   
        	fileNameExtra = fileNameExtra.replaceAll("\\*", "\\.\\*");   
  
            this.fileNameExtra = fileNameExtra.toLowerCase();   
        }   
  
        public boolean accept(File pathname) {   
            if (pathname.isDirectory()) {   
                return true;   
            }   
            if (Pattern.matches(this.fileNameExtra, pathname.getName().toLowerCase())) {   
                return true;   
            }   
            return false;   
        }   
    }   
}   
