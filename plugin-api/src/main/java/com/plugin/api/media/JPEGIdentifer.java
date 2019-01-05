package com.plugin.api.media;
/**
 * 
 * @author wujf
 *
 */
public class JPEGIdentifer extends AbstractMediaIdentifer {
    public static final String WEB_CONTENTTYPE     = "image/jpeg";
    public static String[]     FLIENAME_SUFFIXLIST = {"jpg", "jpeg", "jpe" };
    
    public boolean analyse(byte[] data) {
        if ((data.length > 3) && (data[0] == 255) && (data[1] == 216)
            && (data[2] == 255)) {
            this.b_IKnowIt = true;
        }
        return this.b_IKnowIt;
    }
    
    String returnContentType() {
        return "image/jpeg";
    }
    
    String[] returnFileNameSuffix() {
        return FLIENAME_SUFFIXLIST;
    }
}
