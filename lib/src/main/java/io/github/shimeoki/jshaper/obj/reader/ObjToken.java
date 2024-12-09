package io.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

public enum ObjToken {

    NIL(""),
    COMMENT(ObjTokenizer.COMMENT),
    VERTEX(ObjTokenizer.VERTEX),
    TEXTURE_VERTEX(ObjTokenizer.TEXTURE_VERTEX),
    VERTEX_NORMAL(ObjTokenizer.VERTEX_NORMAL),
    PARAMETER_SPACE_VERTEX(ObjTokenizer.PARAMETER_SPACE_VERTEX),
    FACE(ObjTokenizer.FACE),
    GROUP_NAME(ObjTokenizer.GROUP_NAME);

    private final String txt;

    private ObjToken(final String txt) {
        this.txt = Objects.requireNonNull(txt);
    }

    @Override
    public String toString() {
        return txt;
    }

    public boolean is(final ObjToken other) {
        if (other == null) {
            return false;
        } else {
            return equals(other);
        }
    }
}
