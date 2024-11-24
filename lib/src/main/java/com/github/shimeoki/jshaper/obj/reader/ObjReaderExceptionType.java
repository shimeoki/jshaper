package com.github.shimeoki.jshaper.obj.reader;

public enum ObjReaderExceptionType {

    NONE(""),
    IO("io:"),
    PARSE("parse:");

    private final String txt;

    private ObjReaderExceptionType(final String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return txt;
    }
}
