package io.github.shimeoki.jshaper.obj;

import java.util.Objects;

public final class Token {

    public static final String NIL = "";
    public static final String COMMENT = "#";
    public static final String VERTEX = "v";
    public static final String TEXTURE_VERTEX = "vt";
    public static final String VERTEX_NORMAL = "vn";
    public static final String PARAMETER_VERTEX = "vp";
    public static final String FACE = "f";
    public static final String GROUP = "g";

    public enum Type {
        NIL,
        COMMENT,
        VERTEX,
        TEXTURE_VERTEX,
        VERTEX_NORMAL,
        PARAMETER_VERTEX,
        FACE,
        GROUP
    }

    private final String text;
    private final Type type;

    public Token(final String text) {
        this.text = Objects.requireNonNull(text);
        this.type = parse(text);
    }

    public static Type parse(final String text) {
        switch (Objects.requireNonNull(text)) {
            case Token.COMMENT:
                return Token.Type.COMMENT;
            case Token.VERTEX:
                return Token.Type.VERTEX;
            case Token.TEXTURE_VERTEX:
                return Token.Type.TEXTURE_VERTEX;
            case Token.VERTEX_NORMAL:
                return Token.Type.VERTEX_NORMAL;
            case Token.PARAMETER_VERTEX:
                return Token.Type.PARAMETER_VERTEX;
            case Token.FACE:
                return Token.Type.FACE;
            case Token.GROUP:
                return Token.Type.GROUP;
            default:
                return Token.Type.NIL;
        }
    }

    public String text() {
        return text;
    }

    public Type type() {
        return type;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean typeIs(final Token.Type type) {
        if (type == null) {
            return false;
        } else {
            return this.type.equals(type);
        }
    }

    public boolean textIs(final String text) {
        if (text == null) {
            return false;
        } else {
            return this.text.equals(text);
        }
    }
}
