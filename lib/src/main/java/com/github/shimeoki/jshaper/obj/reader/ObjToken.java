package com.github.shimeoki.jshaper.obj.reader;

public enum ObjToken {

    COMMENT("#"),
    VERTEX("v"),
    TEXTURE_VERTEX("vt"),
    VERTEX_NORMAL("vn"),
    PARAMETER_SPACE_VERTEX("vp"),
    FACE("f");

    private final String txt;

    private ObjToken(final String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return txt;
    }
}
