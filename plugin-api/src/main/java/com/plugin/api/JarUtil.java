package com.plugin.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class JarUtil {
    
    public static void main(String[] args) {
        
        System.out.println("---------------------------");
        
        if (args != null && args.length != 3) {
            System.err.println("param is error");
        } else {
            
            String type = args[0];
            String source = args[1];// D:\\work\\yw\\lib
            String target = args[2];// D:\\work\\ws\\lib
            
            System.out.println("type: " + type);
            System.out.println("source: " + source);
            System.out.println("target: " + target);
            System.out.println();
            
            if ("1".equals(type)) {
                remove(source, target);
            } else if ("2".equals(type)) {
                cut(source, target);
            } else if ("3".equals(type)) {
                replace(source, target);
            }
            System.out.println("---------------------------");
        }
    }
    
    /**
     * 在source中将target中存在的包删除
     */
    public static void remove(String source, String target) {
        
        File ywfile = new File(source);
        File wsfile = new File(target);
        
        if (ywfile.exists() && wsfile.exists()) {
            File[] ywfiles = ywfile.listFiles();
            File[] wsfiles = wsfile.listFiles();
            
            int n = 0;
            for (int i = 0; i < ywfiles.length; i++) {
                File ywfilesub = ywfiles[i];
                String ywname = ywfilesub.getName();
                boolean b = false;
                for (int j = 0; j < wsfiles.length; j++) {
                    File wsfilesub = wsfiles[j];
                    String wsname = wsfilesub.getName();
                    
                    if (ywname.equals(wsname)) {// 如果业务中的包在ws中存在
                        b = true;
                        break;
                    }
                }
                
                if (b) {
                    System.out.println("删除" + ++n + ": " + ywname);
                    deleteFile(ywfilesub);
                }
            }
        }
    }
    
    /**
     * 如果source（业务）中的包在target中不存在，则剪切到target(ws框架)
     */
    public static void cut(String source, String target) {
        
        File ywfile = new File(source);
        File wsfile = new File(target);
        
        if (ywfile.exists() && wsfile.exists()) {
            File[] ywfiles = ywfile.listFiles();
            File[] wsfiles = wsfile.listFiles();
            
            int n = 0;
            for (int i = 0; i < ywfiles.length; i++) {
                File ywfilesub = ywfiles[i];
                String ywname = ywfilesub.getName();
                boolean b = false;
                for (int j = 0; j < wsfiles.length; j++) {
                    File wsfilesub = wsfiles[j];
                    String wsname = wsfilesub.getName();
                    
                    if (ywname.equals(wsname)) {// 如果业务中的包在ws中存在
                        b = true;
                        break;
                    }
                }
                
                if (!b) {
                    System.out.println("剪切" + ++n + ": " + ywname);
                    cutFile(ywfilesub, target);// 将业务包剪切入ws框架
                }
            }
        }
    }
    
    /**
     * 如果targer中有source(业务)中的包，target(ws框架)中的包将被替换
     */
    public static void replace(String source, String target) {
        
        File ywfile = new File(source);// 业务
        File wsfile = new File(target);// ws框架
        
        if (ywfile.exists() && wsfile.exists()) {
            File[] wsfiles = wsfile.listFiles();
            File[] ywfiles = ywfile.listFiles();
            
            int n = 0;
            
            for (int j = 0; j < wsfiles.length; j++) {
                File wsfilesub = wsfiles[j];
                String wsname = wsfilesub.getName();
                
                boolean b = false;
                File ywfilesub = null;
                
                for (int i = 0; i < ywfiles.length; i++) {
                    ywfilesub = ywfiles[i];
                    String ywname = ywfilesub.getName();
                    
                    if (ywname.equals(wsname)) {// targer中有source中的包
                        b = true;
                        break;
                    }
                }
                
                if (b) {
                    deleteFile(wsfilesub);
                    cutFile(ywfilesub, target);// source(业务)中的包剪切到target中
                    
                    System.out.println("替换" + ++n + ": " + wsname);
                }
            }
        }
    }
    
    /**
     * 剪切文件
     */
    private static void cutFile(String old, String target) {
        cutFile(new File(old), target);
    }
    
    private static void cutFile(File sourceFile, String target) {
        copyFile(sourceFile, target);
        deleteFile(sourceFile);
    }
    
    /**
     * 拷贝文件
     */
    private static void copyFile(String source, String target) {
        copyFile(new File(source), target);
    }
    
    private static void copyFile(File source, String target) {
        if (source.exists() && source.isFile()) {
            try {
                FileInputStream input = new FileInputStream(source);
                FileOutputStream output = new FileOutputStream(target
                    + File.separator + (source.getName()).toString());
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 删除文件
     * 
     * @param fileName
     *            要删除的文件的文件名
     * @return 删除成功返回true，否则返回false
     */
    private static boolean deleteFile(String fileName) {
        return deleteFile(new File(fileName));
    }
    
    /**
     * 删除文件
     * 
     * @param fileName
     *            要删除的文件的文件名
     * @return 删除成功返回true，否则返回false
     */
    private static boolean deleteFile(File file) {
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }
}
