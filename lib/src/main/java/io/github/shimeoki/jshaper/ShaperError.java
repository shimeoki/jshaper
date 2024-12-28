package io.github.shimeoki.jshaper;

import java.util.Objects;

public class ShaperError extends Exception {

    private static final String err = "jshaper";
    private final Type type;

    public ShaperError(final Type t, final String msg) {
        super(String.format("%s: %s %s",
                err, Objects.requireNonNull(t), Objects.requireNonNull(msg)));
        type = t;
    }

    public Type type() {
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
