package com.plugin.api.media;
/**
 * 
 * @author wujf
 *
 */
public class GIFIdentifer extends AbstractMediaIdentifer {
    public static final String WEB_CONTENTTYPE     = "image/gif";
    public static String[]     FLIENAME_SUFFIXLIST = {"gif" };
    
    public boolean analyse(byte[] data) {
        if ((data.length > 3) && (data[0] == 71) && (data[1] == 73)
            && (data[2] == 70)) {
            this.b_IKnowIt = true;
        }
        return this.b_IKnowIt;
    }
    
    String returnContentType() {
        return "image/gif";
    }
    
    String[] returnFileNameSuffix() {
        return FLIENAME_SUFFIXLIST;
    }
}
