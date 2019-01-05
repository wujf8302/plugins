package com.plugin.api;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/**
 * 
 * @author wujf
 */
public class PatternCheck {
    public static final String isValidDate           = "^((1[6-9]|[2-9]\\d)\\d{2})-(((0[13578]|1[02])-31)|((0[1,3-9]|1[0-2])-(29|30)))$|^(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)))-(02-29)$|^((1[6-9]|[2-9]\\d)\\d{2})-((0[1-9])|(1[0-2]))-(0[1-9]|1\\d|2[0-8])$";
    public static final String isValidDateOnlyNumber = "^((1[6-9]|[2-9]\\d)?\\d{2})(((0[13578]|1[02])31)|((0[1,3-9]|1[0-2])(29|30)))$|^(((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)))(0229)$|^((1[6-9]|[2-9]\\d)?\\d{2})((0[1-9])|(1[0-2]))(0[1-9]|1\\d|2[0-8])$";
    public static final String isDecimal             = "^(([1-9][0-9]*)|[0-9])((\\.(\\d{1,2}))|)$";
    public static final String isPassword            = "^[\\p{Graph}]{6,20}$";
    public static final String dynamicPassword       = "^[0-9a-zA-Z]{1,20}$";
    public static final String isNumber              = "^[0-9]+$";
    public static final String isNumOrLetter         = "^[0-9a-zA-Z]+$";
    public static final String isWord                = "^[\\w]+$";
    public static final String isPGraph              = "^[\\p{Graph}]+$";
    public static final String isEmail               = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public static final String isTime                = "^(([0-1][0-9])|(2[0-3]))([0-5][0-9])([0-5][0-9])$";
    public static final String isURL                 = "^(http|https)://.*$";
    
    public static boolean validateFormat(String formValue, String formatString) {
        boolean match = true;
        if ((formatString == null) || (formatString.trim().length() == 0))
            return match;
        try {
            match = Pattern.matches(formatString, formValue);
        } catch (PatternSyntaxException localPatternSyntaxException) {
        }
        
        if (formValue.indexOf("'") >= 0)
            match = false;
        return match;
    }
    
    public static boolean isNumber(String value) {
        return validateFormat(value, "^[0-9]+$");
    }
    
    public static boolean isNumOrLett(String value) {
        return validateFormat(value, "^[0-9a-zA-Z]+$");
    }
    
    public static boolean isWord(String value) {
        return validateFormat(value, "^[\\w]+$");
    }
    
    public static boolean isPGraph(String value) {
        return validateFormat(value, "^[\\p{Graph}]+$");
    }
    
    public static boolean isAmt(String value) {
        return validateFormat(value,
            "^(([1-9][0-9]*)|[0-9])((\\.(\\d{1,2}))|)$");
    }
    
    public static boolean isValidDateTime(String value) {
        return (validateFormat(
            value.substring(0, 8),
            "^((1[6-9]|[2-9]\\d)?\\d{2})(((0[13578]|1[02])31)|((0[1,3-9]|1[0-2])(29|30)))$|^(((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)))(0229)$|^((1[6-9]|[2-9]\\d)?\\d{2})((0[1-9])|(1[0-2]))(0[1-9]|1\\d|2[0-8])$"))
            && (validateFormat(value.substring(8),
                "^(([0-1][0-9])|(2[0-3]))([0-5][0-9])([0-5][0-9])$"));
    }
    
    public static boolean isValidDate(String value) {
        return validateFormat(
            value,
            "^((1[6-9]|[2-9]\\d)?\\d{2})(((0[13578]|1[02])31)|((0[1,3-9]|1[0-2])(29|30)))$|^(((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)))(0229)$|^((1[6-9]|[2-9]\\d)?\\d{2})((0[1-9])|(1[0-2]))(0[1-9]|1\\d|2[0-8])$");
    }
    
    public static boolean isURL(String value) {
        value = value.toLowerCase();
        return validateFormat(value, "^(http|https)://.*$");
    }
    
    public static boolean isEmail(String value) {
        return validateFormat(
            value,
            "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
    }
    
    public static void main(String[] args) {
        System.err.println(isNumber("000000aa00000012"));
    }
}
