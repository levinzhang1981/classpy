package com.github.zxh.classpy.classfile;

@SuppressWarnings("serial")
public class ClassParseException extends RuntimeException {

    public ClassParseException(String message) {
        super(message);
    }

    public ClassParseException(Throwable cause) {
        super(cause);
    }
    
}
