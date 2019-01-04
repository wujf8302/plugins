package com.plugin.api.clazz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class JavaCompile {
    
    private static String enter = null;
    
    private void print() {
        Properties props = System.getProperties();
        Set set = props.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(key + " = " + props.getProperty(key));
        }
        System.out.println(System.getenv("JAVA_HOME"));
    }
    
    public static void main(String[] args) throws Exception {
        // compile("d:/src/Test.java");
        String path = System.getProperty("sun.boot.library.path");
        System.out.println(path);
        
    }
    
    public static void compile(String fileName) {
        if (fileName != null && !"".equals(fileName)) {
            return;
        }
        File file = new File(fileName);
        if (file.exists()) {
            if (file.isFile()) {
                compileFile(file);
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    compileFile(files[i]);
                }
            }
        }
    }
    
    private static void compileFile(File file) {
        int status = compile(getArgs(file.getName()));
        if (status != 0) {
            System.out.println(file.getAbsolutePath() + "没有成功编译");
        }
    }
    
    private static int compile(String[] args){
        int status = -1;
        try {
            //jdk1.6以上api
            // JavaCompiler jc = ToolProvider.getSystemJavaCompiler() ;
            // status = jc.run(null,null,null,getArgs(fileName));
            
            //命令行方式
            //D:\src>javac -d d:\src Test.java
            //D:\src>java com.plugin.api.Test
            String bat = "javac " + args[0] + " " + args[1] + " " + args[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    private static String[] getArgs(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "bin";// 当前工作目录
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return new String[] {"-d", path, fileName};
    }
    
    /**
     * 将字符串写入文件
     */
    public static void writeJavaFile(String filePath, String val) {
        String s = "";
        try {
            BufferedReader in = new BufferedReader(new StringReader(val));
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
            while ((s = in.readLine()) != null) {
                out.println(s);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 按行读取java文件
     */
    private static String readJavaFile(String fileUrl) throws IOException {
        String result = "";
        String line = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(fileUrl)));
            while ((line = in.readLine()) != null) {
                result += (line + enter);
            }
            return result;
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
        }
    }
    
    public static int randomInt(int upLimit, int downLimit) {
        return (int) (Math.random() * (upLimit - downLimit)) + downLimit;
    }
    
    public static String random() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date()) + randomInt(1,10);
    }
    
    public static String getEnter() {
        if(enter == null){
            String c = osType();
            if ("windows".equals(c)) {
                enter = "\r\n";
            }else{
                enter = "\n";
            }
        }
        return enter;
    }
    
    private static String osType() {
        String tmp = System.getProperty("os.name");
        String string = "";
        if (tmp.indexOf("Linux") != -1 || tmp.indexOf("linux") != -1) {
            string = "linux";
        } else if (tmp.indexOf("Windows") != -1 || tmp.indexOf("windows") != -1) {
            string = "windows";
        }
        return string;
    }
    
}
