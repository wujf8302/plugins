package com.plugin.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 产生随机数的工具类。
 */
public class RandomUtil {
    
    private static Logger       log        = Logger.getLogger(RandomUtil.class);
    
    private static final Random random     = new Random();
    // ===
    public static final String  allChar    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String  letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String  numberChar = "0123456789";
    
    // ===
    private RandomUtil() {
        // random = new Random();
    }
    
    /**
     * 生成num个长度为length的字符串（字符串各不相同）,字符串只包含字母
     * 
     * @param length
     *            字符串的长度
     * @param num
     *            字符串的个数
     */
    public static String[] random(final int length, final int num) {
        return new RandomUtil().buildRandom(length, num);
    }
    
    /**
     * 生成长度为length的字符串,字符串只包含数字
     * 
     * @param length
     *            字符串的长度
     * @return
     */
    public static String random(final int length) {
        return new RandomUtil().buildRandom(length);
    }
    
    /**
     * 生成num个长度为length的字符串，组成如 123-123-123 格式(只包含数字)
     */
    public static String randombunch(int length, int num) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++) {
            str.append(RandomUtil.random(length));
            if (i != num - 1)
                str.append("-");
        }
        return str.toString();
    }
    
    /**
     * 生成num个长度为length的字符串（字符串各不相同）,字符串只包含字母
     * 
     * @param length
     *            字符串的长度
     * @param num
     *            字符串的个数
     * @return
     */
    private String[] buildRandom(final int length, final int num) {
        if (num < 1 || length < 1) {
            return null;
        }
        Set<String> tempRets = new HashSet<String>(num); // 存放临时结果，以避免重复值的发生
        // 生成num个不相同的字符串
        while (tempRets.size() < num) {
            tempRets.add(buildRandom(length));
        }
        String[] rets = new String[num];
        rets = tempRets.toArray(rets);
        return rets;
    }
    
    /**
     * 返回指定位数的整数
     */
    public static int buildIntRandom(final int length) {
        String maxStr = StringUtils.rightPad("1", length + 1, '0');
        long max = Long.parseLong(maxStr);
        long i = Math.abs(random.nextLong()) % max;
        String rand = String.valueOf(i);
        return Integer.parseInt(rand);
    }
    
    /**
     * 取小于指定范围内的整数
     */
    public static int buildIntRandomBy(final int length) {
        return (int) (Math.random() * length);
    }
    
    /**
     * 生成长度为length的字符串,字符串只包含数字
     * 
     * @param length
     *            字符串的长度
     */
    private String buildRandom(final int length) {
        // 长度为length的最多整数
        String maxStr = StringUtils.rightPad("1", length + 1, '0');
        long max = Long.parseLong(maxStr);
        long i = random.nextLong(); // 取得随机数
        i = Math.abs(i) % max; // 取正数，并限制其长度
        String value = StringUtils.leftPad(String.valueOf(i), length, '0');
        return value;
    }
    
    // ============================================================
    /**
     * 返回一个定长的随机字符串（只包含大小写字母、数字）.
     * @param length随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
    
    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     * 
     * @param length随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }
    
    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     * 
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }
    
    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     * 
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }
    
    /**
     * 生成一个定长的纯0字符串
     * 
     * @param length字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
    
    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     * 
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }
    
    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     * 
     * @param num
     *            数字
     * @param fixdlenth字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }
    
    // ============================================================
    public static Integer randomInt(int upLimit, int downLimit) {
        return (int) (Math.random() * (upLimit - downLimit)) + downLimit;
    }
    
    private static long randomLong(long min, long max) {
        return Math.round(Math.random() * (max - min) + min);
    }
    
    private static Map<Integer, Long[]> baseLong(int len) {
        Map<Integer, Long[]> map = new HashMap<Integer, Long[]>();
        String mixLen = "1";
        String maxLen = "9";
        for (int i = 0; i < len - 1; i++) {
            mixLen += "0";
            maxLen += "9";
        }
        map.put(len,
            new Long[] {Long.parseLong(mixLen), Long.parseLong(maxLen) });
        return map;
    }
    
    public static Long randomLong(int len) {
        Map map = RandomUtil.baseLong(len);
        Long[] baseLong = (Long[]) map.get(len);
        long temp = randomLong(baseLong[0], baseLong[1]);
        return temp;
    }
    
    private static Map<Integer, Integer[]> baseInt(int len) {
        Map<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
        if (len == 1)
            map.put(len, new Integer[] {1, 10 });
        if (len == 2)
            map.put(len, new Integer[] {10, 99 });
        if (len == 3)
            map.put(len, new Integer[] {100, 999 });
        if (len == 4)
            map.put(len, new Integer[] {1000, 9999 });
        if (len == 5)
            map.put(len, new Integer[] {10000, 99999 });
        if (len == 6)
            map.put(len, new Integer[] {100000, 999999 });
        if (len == 7)
            map.put(len, new Integer[] {1000000, 9999999 });
        if (len == 8)
            map.put(len, new Integer[] {10000000, 99999999 });
        if (len == 9)
            map.put(len, new Integer[] {100000000, 999999999 });
        return map;
    }
    
    public static Integer randomInt(int len) {
        Map map = RandomUtil.baseInt(len);
        Integer[] baseInt = (Integer[]) map.get(len);
        return RandomUtil.randomInt(baseInt[0], baseInt[1]);
    }
    
    /**
     * 获取16位随机数
     */
    public static String generateNo() {
        String kk = getRandomString(); // 10位
        kk = getToday("yyyyMMddHHmmss") + kk.substring(kk.length() - 2);// 补充2位的随机号
        return kk;
    }
    
    public static String getRandomString() {
        Random rand = new Random(System.currentTimeMillis());
        if (rand == null) {
            rand = new Random(System.currentTimeMillis());
        }
        return Integer.toString(rand.nextInt());
    }
    
    public static String getToday(String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    public static void main(String[] args) {
        System.out.println(generateString(15));
        System.out.println(generateMixString(15));
        System.out.println(generateLowerString(15));
        System.out.println(generateUpperString(15));
        System.out.println(generateZeroString(15));
        System.out.println(toFixdLengthString(123, 15));
        System.out.println(toFixdLengthString(123L, 15));
        
        // Long 最长19
        // Integer 最长9
        for (int i = 0; i < 1000; i++) {
            System.out.println(randomLong(12).toString());
        }
        System.out.println(generateNo());
    }

    private static int getRandomNumber() {
        return random.nextInt(1000000000);
    }
    
    private static double getMathRandom() {
        return Math.random();
    }
}
