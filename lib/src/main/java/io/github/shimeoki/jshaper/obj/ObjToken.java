package io.github.shimeoki.jshaper.obj;

import java.util.Objects;

public final class ObjToken {

    public static final String NIL = "";
    public static final String COMMENT = "#";
    public static final String VERTEX = "v";
    public static final String TEXTURE_VERTEX = "vt";
    public static final String VERTEX_NORMAL = "vn";
    public static final String PARAMETER_SPACE_VERTEX = "vp";
    public static final String FACE = "f";
    public static final String GROUP_NAME = "g";

    public enum Type {
        NIL,
        COMMENT,
        VERTEX,
        TEXTURE_VERTEX,
        VERTEX_NORMAL,
        PARAMETER_SPACE_VERTEX,
        FACE,
        GROUP_NAME
    }

    private final String text;
    private final Type type;

    public ObjToken(final String text) {
        this.text = Objects.requireNonNull(text);
        this.type = parse(text);
    }

    public static Type parse(final String text) {
        switch (Objects.requireNonNull(text)) {
            case ObjToken.COMMENT:
                return ObjToken.Type.COMMENT;
            case ObjToken.VERTEX:
                return ObjToken.Type.VERTEX;
            case ObjToken.TEXTURE_VERTEX:
                return ObjToken.Type.TEXTURE_VERTEX;
            case ObjToken.VERTEX_NORMAL:
                return ObjToken.Type.VERTEX_NORMAL;
            case ObjToken.PARAMETER_SPACE_VERTEX:
                return ObjToken.Type.PARAMETER_SPACE_VERTEX;
            case ObjToken.FACE:
                return ObjToken.Type.FACE;
            case ObjToken.GROUP_NAME:
                return ObjToken.Type.GROUP_NAME;
            default:
                return ObjToken.Type.NIL;
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

    public boolean typeIs(final ObjToken.Type type) {
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
