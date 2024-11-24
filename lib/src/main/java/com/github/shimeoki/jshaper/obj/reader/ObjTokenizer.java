package com.github.shimeoki.jshaper.obj.reader;

public final class ObjTokenizer {

    public static final String COMMENT = "#";
    public static final String VERTEX = "v";
    public static final String TEXTURE_VERTEX = "vt";
    public static final String VERTEX_NORMAL = "vn";
    public static final String PARAMETER_SPACE_VERTEX = "vp";
    public static final String FACE = "f";
    public static final String GROUP_NAME = "g";

    public static ObjToken parse(final String s) {
        switch (s) {
            case COMMENT:
                return ObjToken.COMMENT;
            case VERTEX:
                return ObjToken.VERTEX;
            case TEXTURE_VERTEX:
                return ObjToken.TEXTURE_VERTEX;
            case VERTEX_NORMAL:
                return ObjToken.VERTEX_NORMAL;
            case PARAMETER_SPACE_VERTEX:
                return ObjToken.PARAMETER_SPACE_VERTEX;
            case FACE:
                return ObjToken.FACE;
            case GROUP_NAME:
                return ObjToken.GROUP_NAME;
            default:
                return null;
        }
    }
}
