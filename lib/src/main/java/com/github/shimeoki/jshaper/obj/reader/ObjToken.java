package com.github.shimeoki.jshaper.obj.reader;

public enum ObjToken {

    COMMENT(ObjTokenizer.COMMENT),
    VERTEX(ObjTokenizer.VERTEX),
    TEXTURE_VERTEX(ObjTokenizer.TEXTURE_VERTEX),
    VERTEX_NORMAL(ObjTokenizer.VERTEX_NORMAL),
    PARAMETER_SPACE_VERTEX(ObjTokenizer.PARAMETER_SPACE_VERTEX),
    FACE(ObjTokenizer.FACE);

    private final String txt;

    private ObjToken(final String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return txt;
    }
}
