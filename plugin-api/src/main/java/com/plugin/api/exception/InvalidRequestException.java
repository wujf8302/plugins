package com.plugin.api.exception;

public class InvalidRequestException extends Exception {
    private static final long serialVersionUID = 7595048177340914517L;
    private Throwable         m_ex;
    
    public InvalidRequestException() {
        super();
    }
    
    public InvalidRequestException(String sMsg) {
        super(sMsg, null);
    }
    
    public InvalidRequestException(String sMsg, Throwable ex) {
        super(sMsg, null);
        this.m_ex = ex;
    }
    
    public Throwable getException() {
        return this.m_ex;
    }
    
    public Throwable getCause() {
        return this.m_ex;
    }
}
