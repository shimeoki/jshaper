package com.github.shimeoki.jshaper.obj.reader;

public class ObjReaderException extends Exception {

    private static final String err = "ObjReader: ";

    public ObjReaderException(final ObjReaderExceptionType type, final String msg) {
        super(String.format("%s %s %s", err, type, msg));
    }
}
