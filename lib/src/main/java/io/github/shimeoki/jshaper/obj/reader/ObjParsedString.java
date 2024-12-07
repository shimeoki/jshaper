package io.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

public final class ObjParsedString {

    private final ObjToken token;
    private final String value;

    public ObjParsedString(final String value) {
        this.value = Objects.requireNonNull(value);
        this.token = ObjTokenizer.parse(value);
    }

    public ObjToken token() {
        return token;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("token \"%s\": value \"%s\"", token, value);
    }
}
