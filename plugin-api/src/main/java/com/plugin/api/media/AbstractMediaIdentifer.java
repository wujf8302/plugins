package com.plugin.api.media;

public abstract class AbstractMediaIdentifer implements MediaRecogniser {
    protected boolean b_IKnowIt = false;
    
    public abstract boolean analyse(byte[] paramArrayOfByte);
    
    abstract String[] returnFileNameSuffix();
    
    abstract String returnContentType();
    
    public String[] getFileNameSuffix() {
        if (!this.b_IKnowIt) {
            throw new UnknowMediaFormatException("Unknow media format");
        }
        return returnFileNameSuffix();
    }
    
    public String getWebContentType() {
        if (!this.b_IKnowIt) {
            throw new UnknowMediaFormatException("Unknow media format");
        }
        return returnContentType();
    }
}
