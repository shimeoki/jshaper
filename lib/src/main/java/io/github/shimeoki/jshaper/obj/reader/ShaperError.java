package io.github.shimeoki.jshaper.obj.reader;

public class ShaperError extends Exception {

    private static final String err = "ObjReader";
    private final ObjReaderExceptionType type;

    public ShaperError(final ObjReaderExceptionType type, final String msg) {
        super(String.format("%s: %s %s", err, type, msg));
        this.type = type;
    }

    public ObjReaderExceptionType type() {
        return type;
    }

    public enum Type {

        NONE(""),
        IO("io:"),
        PARSE("parse:");

        private final String txt;

        private Type(final String txt) {
            this.txt = txt;
        }

        @Override
        public String toString() {
            return txt;
        }
    }
}
