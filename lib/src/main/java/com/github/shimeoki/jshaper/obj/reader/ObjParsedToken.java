package com.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

public final class ObjParsedToken {

    private final ObjToken token;
    private final String value;

    public ObjParsedToken(final ObjToken token, final String value) {
        Objects.requireNonNull(value);

        this.token = token;
        this.value = value;
    }

    public ObjToken token() {
        return token;
    }

    public String value() {
        return value;
    }
}
