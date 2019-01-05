package com.plugin.api.media;
/**
 * 
 * @author wujf
 *
 */
public abstract interface MediaRecogniser {
    public abstract boolean analyse(byte[] paramArrayOfByte);
    
    public abstract String[] getFileNameSuffix()
        throws UnknowMediaFormatException;
    
    public abstract String getWebContentType()
        throws UnknowMediaFormatException;
}
