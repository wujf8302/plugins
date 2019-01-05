package com.plugin.api.media;
/**
 * 
 * @author wujf
 *
 */
public class UnknowMediaFormatException extends RuntimeException {
    private static final long serialVersionUID = -8319505572138290645L;
    
    public UnknowMediaFormatException() {
    }
    
    public UnknowMediaFormatException(String message) {
        super(message);
    }
    
    public UnknowMediaFormatException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UnknowMediaFormatException(Throwable cause) {
        super(cause);
    }
}
