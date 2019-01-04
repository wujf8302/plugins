package com.plugin.api;

public class ConstantUtil {
	
	/**
	 * 数据源
	 */
	public interface DataSource{
		public String TYPE = "type";
		public String DRIVER = "driver";
		public String URL = "url";
		public String USER = "user";
		public String PWD = "pwd";
	}
	
	/**
	 * 导入
	 */
	public interface ImportUtil{
		public static String DATASOURCE_DEFAULT_NAME = "oracle39mspstat";
	}
	
	/**
	 * 校验
	 */
	public interface ValidatorUtil{
		
		 public static String RootName = "verify";
		 
		 public static String VerifyXMLFile = "-verify.xml";
		 
		 public interface ValidatorTool{
				public static final String isLettOrNumbOrNnderline = "^[a-zA-Z0-9][a-zA-Z0-9_-]{0,15}$";// 15位以内字母，数字，下划线
				public static final String isChinaOrNumbOrLett = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";         // 判断是否是汉字、字母、数字组成

				public static final String isNumberOrLetter = "^[0-9a-zA-Z]+$"; // 判断是否是数字或字母
				public static final String email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\\\w+)*\\.\\w+([-.]\\w+)*$"; // 电子信箱
				public static final String phone = "^((\\(\\d{3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}$|[0-9]{12}"; // 固定电话
				// 059188888888
				// 0591-88888888

				// public static final String mobile =
				// "^((\\(\\d{3}\\))|(\\d{3}\\-))?13\\d{9}$";
				public static final String mobile = "(^0?[1][358][0-9]{9}$)"; // 手机号码
				public static final String phoneOrmobile = "^((\\(\\d{3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}$|(^0?[1][358][0-9]{9}$)";// 手机或固话

				public static final String url = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$"; // 网址

				public static final String idCard = "^\\d{15}(\\d{2}[A-Za-z0-9])?$"; // 身份证号码

				public static final String number = "^\\d+$"; // 数字
				public static final String integer = "^[-\\+]?\\d+$"; // 正负数字
				public static final String doubleOrFloat = "^[-\\+]?\\d+(\\.\\d+)?$"; // 浮点数、双精度

				public static final String zip = "^[1-9]\\d{5}$"; // 邮政编码
				public static final String qq = "^[1-9]\\d{4,8}$"; // qq号码
				public static final String english = "^[A-Za-z]+$"; // 英文字符
				public static final String chinese = "^[\u0391-\uFFE5]+$"; // 中文字符

				public static final String imgReg = "[.{1}](jpeg|JPEG|GIF|gif|bmp|BMP|jpg|JPG|PNG|png){1}$"; // 图片类型

				public static final String unSafe = "^(([A-Z]*|[a-z]*|\\d*|[-_\\~!@#\\$%\\^&\\*\\.\\(\\)\\[\\]\\{\\}<>\\?\\\\\\/\\\'\\\"]*)|.{0,5})$|\\s";// 密码

				public static final String date = "date"; // 日期
				// [\\d]{4}\\-[\\d]{2}\\-[\\d]{2}
				public static final String assign = "assign"; // 自定义正则表达式
				public static final String file = "file"; // 上传文件
				public static final String doubles = "doubles"; // 浮点数
			}
		 
	}
	
    public static class Regex {
		
		public static String createEnglishRegex(int start,int end){//验证英文字符的正则表达式
			String regex = "^[\\w|\\W]{" + start + "," + end +"}$";
			return regex;
		}
		
		public static String createEnglishAndChinessRegex(int start,int end){ ////验证英文或中文字符的正则表达式
			String regex = "^[\\u4e00-\\u9fa5|\\w|\\W]{" + start + ","+ end +"}$";
			return regex;
		}

		public interface Verify{
			
			public static final String mobileRegex = "(^0?[1][358][0-9]{9}$)"; //验证手机号码的正则表达式
			public static final String pwdRegex = "^[\\w|\\W]{4,16}$";         //验证密码的正则表达式
			public static final String emailRegex = "^([\\w]+)(.[\\w]+)*@([\\w-]+\\.){1,5}([A-Za-z]){2,4}$"; //验证电子邮箱的正则表达式
			
			public static final String telephoneAndmobileRegex1 = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";                                //验证手机号码或固定电话的正则表达式
			public static final String telephoneAndmobileRegex2 = "\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{12}|(^0?[1][358][0-9]{9}$)"; //验证手机号码或固定电话的正则表达式 严格型
			 
			public static final String telephoneRegex = "0\\d{9}|0\\d{10}|0\\d{11}"; //验证固定电话的正则表达式
			public static final String shuziRegex = "^\\d+$";                        //验证数字的正则表达式
			public static final String floatRegex = "^[1-9]{1}(\\.\\d+)?$";          //验证浮点数字的正则表达式
			public static final String imgRegex = "[.{1}](jpeg|JPEG|GIF|gif|bmp|BMP|jpg|JPG|PNG|png){1}$"; //验证图片后辍的正则表达式
			
			public static final String Email="^([a-z0-9A-Z]+[-\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//邮箱
			public static final String ZH = "^[\u0391-\uFFE5]+$";//中文
			public static final String EN = "^[A-Za-z]+$";//英文字母
			public static final String CAPITALLETTERS = "^[A-Z]+$";//大写字母
			public static final String LOWERCASELETTERS = "^[a-z]+$";//小写字母
			public static final String STR_0 ="^\\w+$";//由数字、26个英文字母(包含大小写)或者下划线组成的字符串
			public static final String STR0 = "^[A-Za-z0-9]+$";//由数字和26个英文字母(包含大小写)组成的字符串
			public static final String QQ = "^[1-9]\\d{4,8}$";//QQ号
			public static final String ZIP = "^[1-9]\\d{5}$";//邮编地址
			public static final String NUMBER = "^\\d+$";//数字
			public static final String IDCARD = "^\\d{15}(\\d{2}[A-Za-z0-9])?$";//18身份证号(不能严格验证)
			public static final String URL = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";
			public static final String PHONE = "^((\\(\\d{3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}$";
			public static final String MOBILE = "^((\\(\\d{3}\\))|(\\d{3}\\-))?1(3|5)\\d{9}$"; 
			public static final String INTEGER = "^[-\\+]?\\d+$";//整数
			public static final String NEGATIVENTEGERS = "^-[0-9]*[1-9][0-9]*$";//负整数
			public static final String DOUBLE = "^[-\\+]?\\d+(\\.\\d+)?$";//Double
			public static final String VCODE = "^[a-zA-Z0-9]{4}$";//验证码
			public static final String DOMAIN = "^([a-zA-Z0-9]|[-]){4,16}$";
			public static final String BANKCARD = "^[1-9][0-9]\\d{14}$";
			public static final String CURRENCY = "^\\d+(\\.\\d+)?$";//货币
			public static final String IDCARD15 = "^\\d{15}$";//15位身份证
		}
		
		public interface Tools{
			/**
	    	 * 匹配图象 
	    	 * 格式:  /相对路径/文件名.后缀 (后缀为gif,dmp,png)
	    	 * 匹配 : /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp
	    	 * 不匹配: c:/admins4512.gif
	    	 */
	    	public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

	    	/**
	    	 * 匹配email地址
	    	 * 格式: XXX@XXX.XXX.XX
	    	 * 匹配 : foo@bar.com 或 foobar@foobar.com.au
	    	 * 不匹配: foo@bar 或 $$$@bar.com
	    	 */
	    	public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

	    	/**
	    	 * 匹配匹配并提取url
	    	 * 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	    	 * 匹配 : http://www.suncer.com 或news://www
	    	 * 
	    	 * 提取(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	    	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	    	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	    	 * matchResult.group(4) = /index.html?login=true
	    	 * 
	    	 * 不匹配: c:\window
	    	 */
	    	public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

	    	/**
	    	 * 匹配并提取http
	    	 * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或
	    	 * https://XXX
	    	 * 
	    	 * 匹配 : http://www.suncer.com:8080/index.html?login=true
	    	 * 
	    	 * 提取(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	    	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	    	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	    	 * matchResult.group(4) = /index.html?login=true
	    	 * 
	    	 * 不匹配: news://www
	    	 */
	    	public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

	    	/**
	    	 * 匹配日期
	    	 * 
	    	 * 格式(首位不为0): XXXX-XX-XX 或 XXXX XX XX 或 XXXX-X-X
	    	 * 范围:1900--2099
	    	 * 匹配 : 2005-04-04
	    	 * 不匹配: 01-01-01
	    	 */
	    	public static final String date_regexp = "^((((19){1}|(20){1})d{2})|d{2})[-\\s]{1}[01]{1}d{1}[-\\s]{1}[0-3]{1}d{1}$";// 匹配日期

	    	/**
	    	 * 匹配电话
	    	 * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或
	    	 * (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或
	    	 * XXXXXXXXXXX(11位首位不为0)
	    	 * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或
	    	 * 010-12345678 或 12345678912
	    	 * 不匹配: 1111-134355 或 0123456789
	    	 */
	    	public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	    	/**
	    	 * 匹配身份证
	    	 * 格式为: XXXXXXXXXX(10位) 或 XXXXXXXXXXXXX(13位) 或 XXXXXXXXXXXXXXX(15位) 或
	    	 * XXXXXXXXXXXXXXXXXX(18位)
	    	 * 匹配 : 0123456789123
	    	 * 不匹配: 0123456
	    	 */
	    	public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

	    	/**
	    	 * 匹配邮编代码
	    	 * 格式为: XXXXXX(6位)
	    	 * 匹配 : 012345
	    	 * 不匹配: 0123456
	    	 */
	    	public static final String ZIP_regexp = "^[0-9]{6}$";// 匹配邮编代码

	    	/**
	    	 * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号<
	    	 * 反斜杠\ 即空格,制表符,回车符等 )
	    	 * 格式为: x 或 一个一上的字符
	    	 * 匹配 : 012345
	    	 * 不匹配: 0123456
	    	 */
	    	public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";// 匹配邮编代码

	    	/**
	    	 * 匹配非负整数（正整数 + 0)
	    	 */
	    	public static final String non_negative_integers_regexp = "^\\d+$";

	    	/**
	    	 * 匹配不包括零的非负整数（正整数 > 0)
	    	 */
	    	public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

	    	/**
	    	 * 匹配正整数
	    	 */
	    	public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";

	    	/**
	    	 * 匹配非正整数（负整数 + 0）
	    	 */
	    	public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

	    	/**
	    	 * 匹配负整数
	    	 */
	    	public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

	    	/**
	    	 * 匹配整数
	    	 */
	    	public static final String integer_regexp = "^-?\\d+$";

	    	/**
	    	 * 匹配非负浮点数（正浮点数 + 0）
	    	 */
	    	public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

	    	/**
	    	 * 匹配正浮点数
	    	 */
	    	public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	    	/**
	    	 * 匹配非正浮点数（负浮点数 + 0）
	    	 */
	    	public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

	    	/**
	    	 * 匹配负浮点数
	    	 */
	    	public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	    	/**
	    	 * 匹配浮点数
	    	 */
	    	public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

	    	/**
	    	 * 匹配由26个英文字母组成的字符串
	    	 */
	    	public static final String letter_regexp = "^[A-Za-z]+$";

	    	/**
	    	 * 匹配由26个英文字母的大写组成的字符串
	    	 */
	    	public static final String upward_letter_regexp = "^[A-Z]+$";

	    	/**
	    	 * 匹配由26个英文字母的小写组成的字符串
	    	 */
	    	public static final String lower_letter_regexp = "^[a-z]+$";

	    	/**
	    	 * 匹配由数字和26个英文字母组成的字符串
	    	 */
	    	public static final String letter_number_regexp = "^[A-Za-z0-9]+$";

	    	/**
	    	 * 匹配由数字、26个英文字母或者下划线组成的字符串
	    	 */
	    	public static final String letter_number_underline_regexp = "^\\w+$";
		}
	}
    
    public interface PageTool {
    	public static final int PAGESIZE = 3;
    	public static final int CURRENTPAGE = 1;
    }

}
