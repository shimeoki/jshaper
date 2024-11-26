package com.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

public final class ObjParsedString {

    private final ObjToken token;
    private final String value;

    public ObjParsedString(final String value) {
        Objects.requireNonNull(value);

        this.token = ObjTokenizer.parse(value);
        this.value = value;
    }

    public ObjToken token() {
        return token;
    }

    public String value() {
        return value;
    }
}
