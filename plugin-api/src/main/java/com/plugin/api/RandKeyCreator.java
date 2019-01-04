package com.plugin.api;

/**
 * 随机串生成工具类.
 * @author wujf
 * @version Revision 1.0.0
 */
public class RandKeyCreator {
    
    public static final long MIN_VALUE = 0x8000000000000000L;
    public static final long MAX_VALUE = 0x7fffffffffffffffL;
    
    final static char[]  digits    = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};
    
    
    public static void main(String arg[]) {
        for (int i = 0; i < 10000000; i++) {
            //System.out.println("第" + i + "个：" + RandKeyCreator.getKey(5));
            System.out.println("第" + i + "个：" + RandKeyCreator.getRand(String.valueOf(i+1)));
        }
    }
    
    /**
     * 随机码(2)＋时间戳(13)＋随机码(3)的生成.
     */
    public static Long getRand() {
        return Long.parseLong(getRand(2) + System.currentTimeMillis() + getRand(3));
    }
    public static Long getRand(String n) {
        return Long.valueOf(n) + Long.parseLong(getRand(2) + System.currentTimeMillis() + getRand(3));
    }
    
    /**
     * 主键生成(例如：4FA3F78P43D0).
     */
    public static String getKey(int num) {
        return getRand(getRand(), num);
    }
    /**
     * j为2的次方，如转成16进制就是4，32进制就是5....
     */
    public static String getRand(long i, int j) {
        return toUnsignedString(i, j);
    }
    private static String toUnsignedString(long i, int shift) {
        String rankKey;
        char[] buf = new char[36];
        int charPos = 36;
        int radix = 1 << shift;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (i & mask)];
            i >>>= shift;
        } while (i != 0);
        rankKey = new String(buf, charPos, (36 - charPos));
        if (rankKey.length() < 12) {
            switch (rankKey.length()) {
                case 11:
                    rankKey += "A";
                    break;
                case 10:
                    rankKey += "BB";
                    break;
                case 9:
                    rankKey += "CCC";
                    break;
                case 8:
                    rankKey += "DDDD";
                    break;
            }
        }
        return rankKey;
    }
    
    /**
     * 生成指定长度的随机串.
     */
    public static String getRand(int len) {
        String str = "";
        while (str.length() != len) {
            str = (Math.random() + "").substring(2, 2 + len);
        }
        return str;
    }
    
    /**
     * 随机id.
     */
    public static Long getKey() {
        return new Long(randomLong(0, 99999999L) + System.currentTimeMillis() );
    }
    
    /**
     * 生成指定长度的随机串.
     */
    private static long randomLong(long upLimit, long downLimit) {
        return (long) (Math.random() * (upLimit - downLimit)) + downLimit;
    }
}
